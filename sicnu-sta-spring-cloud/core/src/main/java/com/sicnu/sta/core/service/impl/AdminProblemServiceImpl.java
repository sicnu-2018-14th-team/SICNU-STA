package com.sicnu.sta.core.service.impl;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.commons.utils.ZipUtils;
import com.sicnu.sta.core.server.*;
import com.sicnu.sta.core.service.AdminProblemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class AdminProblemServiceImpl implements AdminProblemService {

    public static final Integer LEN = 1000;
    public static final Integer TYPE_CHOICE = 2;
    public static final Integer TYPE_PROGRAM = 3;
    public static final String ZIP_TYPE = "application/zip";

    @Resource
    private ProblemServer problemDao;

    @Resource
    private TagServer tagDao;

    @Resource
    private UsersServer userDao;

    @Resource
    private ContestServer contestDao;

    @Resource
    private AnswerServer answerDao;

    @Resource
    private ContestProblemServer contestProblemDao;

    @Resource
    private AdminTagServiceImpl adminTagService;

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * 获取标签-题目字符串
     *
     * @param problemId 题目 id
     * @return 由 problemId 构成的字符串
     */
    @Override
    public String getProblemStr(int problemId) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < LEN; i ++) res.append("0");
        res.setCharAt(problemId, '1');
        return res.toString();
    }

    /**
     * 查找题目信息
     *
     * @param findProblem 查找题目实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryProblemInfoByFindProblemClass(FindProblem findProblem) {
        try {
            List<Integer> problemId = problemDao.queryProblemIdByAuthorIdAndDifficultyAndTypeId(findProblem.getAuthorId(),
                                                                                                findProblem.getDifficulty(),
                                                                                                findProblem.getTypeId());
            // 需要根据标签查找
            if (findProblem.getTags() != null) {
                for (Integer it : findProblem.getTags()) {
                    List<Integer> tmp = queryProblemIdByTag(it);
                    // 取并集
                    problemId.retainAll(tmp);
                }
            }
            List<Map<String, Object>> problems = new ArrayList<>();
            for (Integer it : problemId) {
                Map<String, Object> map1 = problemDao.queryRealIdAndTypeIdByProblemId(it);
                String tableName = problemDao.queryTableNameByTypeId(Math.toIntExact((Long) map1.get("type_id")));
                Map<String, Object> map2 = problemDao.queryProblemInfo(Math.toIntExact((Long) map1.get("real_id")), tableName);
                map2.put("user_name", userDao.queryUserNameByUserId(Math.toIntExact((Long) map2.get("user_id"))));
                map2.put("problem_id", it);
                problems.add(map2);
            }
            return ResultUtils.success(problems);
        } catch (Exception e) {
            log.error("查询题目信息失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 根据标签 id 来查找含有此标签的题目
     *
     * @param tagId 标签 id
     * @return 含有此标签的题目 id 集
     */
    @Override
    public List<Integer> queryProblemIdByTag(int tagId) {
        String problem = tagDao.queryProblemByTag(tagId);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < problem.length(); i ++) {
            if (problem.charAt(i) == '1') res.add(i + 1);
        }
        return res;
    }

    /**
     * 创建选择题
     *
     * @param choiceProblem 选择题实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createChoiceProblem(ChoiceProblem choiceProblem) {
        try {
            problemDao.createChoiceProblem(choiceProblem);
            int choiceProblemId = choiceProblem.getProblemId();
            if (choiceProblemId > 0) {
                Problem problem = new Problem(choiceProblem.getUserId(), choiceProblem.getDifficulty(), choiceProblem.getAvailable(), choiceProblemId, TYPE_CHOICE);
                return addProblemToContest(problem, choiceProblem.getContestId(), choiceProblem.getScore(), choiceProblem.getTagList());
            } else {
                return ResultUtils.fail("创建选择题失败");
            }
        } catch (Exception e) {
            log.error("创建选择题失败");
            return ResultUtils.error();
        }
    }

    /**
     * 解析上传的测试文件压缩包
     *
     * @param file 文件
     * @return ResultUtil
     */
    @Override
    public ResultUtils<Object> unTestCase(MultipartFile file) {

        if (file == null) {
            return ResultUtils.fail("空文件");
        }
        try {
            byte[] bytes = file.getBytes();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String pathStr0 = UPLOAD_FOLDER + uuid + "/0/";
            String pathStr1 = UPLOAD_FOLDER + uuid + "/1/";
            Path path0 = Paths.get(pathStr0);
            Path path1 = Paths.get(pathStr1);
            //如果没有文件夹，则创建
            if (!Files.isWritable(path0)) {
                Files.createDirectories(path0);
            }
            if (!Files.isWritable(path1)) {
                Files.createDirectories(path1);
            }
            Path path = Paths.get(pathStr0 + file.getOriginalFilename());
            Files.write(path, bytes);
            ZipUtils.unzip(path.toString(), pathStr1);
            return validTestCase(pathStr1, uuid, UPLOAD_FOLDER + uuid);
        } catch (IOException e) {
            log.error("解析压缩包失败", e);
            return ResultUtils.error();
        }
    }

    // 校验上传的文件是否合法
    @Override
    public ResultUtils<Object> validTestCase(String path, String UUID, String deletePath) {

        String IN_FILE_TYPE = ".in";
        String OUT_FILE_TYPE = ".out";

        List<String> inFiles = new ArrayList<>();
        List<String> outFiles = new ArrayList<>();

        int flag = 1;

        // 遍历文件夹 验证是否合法
        File[] fs = new File(path).listFiles();
        if (fs == null) flag = 0;
        if (flag == 1) {
            for(File f : fs){
                String fileName = f.getName();
                //获取最后一个.的位置
                int lastIndexOf = fileName.lastIndexOf(".");
                String suffix = fileName.substring(lastIndexOf);
                String prefix = fileName.substring(0, lastIndexOf);
                if (IN_FILE_TYPE.equals(suffix)) {
                    inFiles.add(prefix);
                } else if (OUT_FILE_TYPE.equals(suffix)) {
                    outFiles.add(prefix);
                } else flag = 0;
            }
        }
        if (!ListUtils.isEqualList(inFiles, outFiles)) flag = 0;
        if (flag == 0) {
            deleteDir(deletePath);
            return ResultUtils.fail();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("input", inFiles);
        map.put("output", outFiles);
        TestCasePath testCasePath = new TestCasePath(UUID);
        problemDao.insertNewPath(testCasePath);
        map.put("pathId", testCasePath.getPathId());
        return ResultUtils.success(map);
    }

    /**
     * 创建编程题
     *
     * @param programProblem 编程题实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createProgramProblem(ProgramProblem programProblem) {
        try {
            problemDao.createProgramProblem(programProblem);
            int realId = programProblem.getProblemId();
            if (realId > 0) {
                Problem problem = new Problem(programProblem.getUserId(), programProblem.getDifficulty(), programProblem.getAvailable(), realId, TYPE_PROGRAM);
                addProblemToContest(problem, programProblem.getContestId(), programProblem.getScore(), programProblem.getTagList());
                problemDao.updateProblemIdFromTestCasePath(problem.getProblemId(), programProblem.getPathId());
                return ResultUtils.success();
            } else {
                return ResultUtils.fail("创建编程题失败");
            }
        } catch (Exception e) {
            log.error("创建编程题失败", e);
            return ResultUtils.error();
        }
    }


    // 在比赛下创建题目及完善题目标签
    public ResultUtils<Object> addProblemToContest(Problem problem, Integer contestId, Integer score, List<String> tagList) {
        problemDao.addProblem(problem);
        int problemId = problem.getProblemId();
        if (problemId > 0) {
            // 如果需要向比赛中添加该题目
            if (contestId != null) {
                List<Integer> userIds = contestDao.queryContestUserCnt(contestId);
                for (Integer userId : userIds) {
                    Answer answer = new Answer(userId, contestId, problemId, 0);
                    answerDao.saveUserAnswer(answer);
                }
                ContestProblem contestProblem = new ContestProblem(contestId, problemId, score);
                contestProblemDao.addProblemToContest(contestProblem);
            }
            String tag = adminTagService.getTagStr(tagList, problemId);
            problemDao.setProblemTag(tag, problemId);
            return ResultUtils.success(problemId);
        } else {
            return ResultUtils.fail("把该题添加到总题目列表失败");
        }
    }

    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.isFile()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File value : files) {
                    deleteDir(value.getAbsolutePath());
                }
            }
        }
        boolean flag = file.delete();
    }

    // 读取测试文件的数据
    public static Map<String, List<String>> readTestCase(String path) throws IOException {

        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) return null;
        int cnt = 1;
        List<String> input = new ArrayList<>();
        List<String> output = new ArrayList<>();
        for (File it : files) {
            BufferedReader rd = new BufferedReader(new FileReader(it));
            StringBuilder result = new StringBuilder();
            String line;
            int flag = 0;
            while((line = rd.readLine()) != null) {
                if (flag == 1) result.append("\n");
                result.append(line);
                flag = 1;
            }
            if (cnt % 2 == 1) {
                input.add(result.toString());
            } else {
                output.add(result.toString());
            }
            cnt += 1;
        }
        Map<String, List<String>> data = new HashMap<>();
        data.put("input", input);
        data.put("output", output);
        return data;
    }

}
