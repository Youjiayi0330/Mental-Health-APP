package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Passage;
import com.example.demo.service.PassageService;
import com.example.demo.vo.PassageVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/passage")
public class PassageController {
    @Resource
    private PassageService passageService;
    String finalFileName;

    //选择文章封面
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName=file.getOriginalFilename();
        finalFileName=fileName;
        return Result.success();
    }

    //添加至草稿箱
    @PostMapping("/draft")
    public Result<Passage> addDraft(@RequestBody Passage passage){
        passage.setFileName(finalFileName);
        Passage passageInfo=passageService.add(passage);
        return Result.success(passageInfo);
    }

    //添加至已发送
    @PostMapping("/send")
    public Result<Passage> addSend(@RequestBody Passage passage){
        passage.setFileName(finalFileName);
        Passage passageInfo=passageService.send(passage);
        return Result.success(passageInfo);
    }

    //查看文章详细信息
    @GetMapping("/{pid}")
    public Result<Passage> detail(@PathVariable("pid") Long pid){
        Passage passage=passageService.findById(pid);
        return Result.success(passage);
    }

    //分页查询草稿箱文章
    @GetMapping("/findDraft")
    public Result<PageInfo<Passage>> findDraft(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "2") Integer pageSize){
        return Result.success(passageService.findDraft(pageNum,pageSize));
    }

    //分页查询已发送文章
    @GetMapping("/findSended")
    public Result<PageInfo<Passage>> findSended(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "2") Integer pageSize){
        return Result.success(passageService.findSended(pageNum,pageSize));
    }

    //保存草稿箱文章
    @PutMapping("/updateDraft")
    public Result updateDraft(@RequestBody Passage passage){
        return Result.success(passageService.updateDraft(passage));
    }

    //更新已发布文章
    @PutMapping("/updateHasSended")
    public Result updateHasSended(@RequestBody Passage passage){
        return Result.success(passageService.updateHasSended(passage));
    }

    //根据id删除文章
    @DeleteMapping("/{pid}")
    public Result delete(@PathVariable("pid") Long pid){
        passageService.delete(pid);
        return Result.success();
    }

    //根据标题分页查询草稿箱文章
    @GetMapping("/findByDraftName/{ptitle}")
    public Result<PageInfo<Passage>> findByDraftName(@PathVariable("ptitle") String ptitle,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value="pageSize",defaultValue = "2") Integer pageSize){
        return Result.success(passageService.findByDraftName(ptitle,pageNum,pageSize));
    }

    //根据标题分页查询发布文章
    @GetMapping("/findBySendedName/{ptitle}")
    public Result<PageInfo<PassageVo>> findBySendedName(@PathVariable("ptitle") String ptitle,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value="pageSize",defaultValue = "2") Integer pageSize){
        return Result.success(passageService.findBySendedName(ptitle,pageNum,pageSize));
    }

    @GetMapping("/allPassages")
    public List<PassageVo> findAllPassages(){
        return passageService.findAllPassages();
    }

    //根据文章名模糊查询已发布的文章
    @GetMapping("/findByName/{name}")
    public List<PassageVo> findByName(@PathVariable("name") String name){
        return passageService.findByName(name);
    }

    @GetMapping("/findPopPassage")
    public Result<String> findPopPassage(){
        List<PassageVo> passageVoList = passageService.findAllPassages();
        String passageName = passageVoList.get(0).getPtitle();
        Integer collectCount = passageVoList.get(0).getCollect_count();
        for(int i=1; i<passageVoList.size(); i++){
            if(passageVoList.get(i).getCollect_count() > collectCount){
                passageName = passageVoList.get(i).getPtitle();
                collectCount = passageVoList.get(i).getCollect_count();
            }
        }
        return Result.success(passageName);
    }
}
