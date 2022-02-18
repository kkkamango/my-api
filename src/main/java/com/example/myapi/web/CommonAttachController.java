package com.example.myapi.web;

import com.example.myapi.model.CommonAttach;
import com.example.myapi.model.CommonAttachType;
import com.example.myapi.service.CommonAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class CommonAttachController {

    private CommonAttachService commonAttachService;

    @Autowired
    public CommonAttachController(CommonAttachService commonAttachService) {
        this.commonAttachService = commonAttachService;
    }

    @GetMapping("/common/attach/{id}")
    public CommonAttach getAttach(@PathVariable("id") Integer id){
        return commonAttachService.getCommonAttach(id);
    }

    @GetMapping("/common/attach/{type}/{parentId}")
    public List<CommonAttach> getAttachList(@PathVariable("type") CommonAttachType type
            , @PathVariable("parentId") Integer parentId){

        log.info("saveAttach parentId = {}, type = {}", parentId, type);
        return commonAttachService.getCommonAttachList(parentId, type);
    }

    @PostMapping("/common/attach/{type}/{parentId}")
    public CommonAttach saveAttach(@PathVariable("type") CommonAttachType type
            , @PathVariable("parentId") Integer parentId/*, @RequestBody CommonAttach attach*/
            /*, @RequestPart CommonAttach attach*/
            , @RequestPart MultipartFile file){

        CommonAttach attach = CommonAttach.builder()
                .file(file)
                .parentId(parentId)
                .type(type)
                .build();

        log.info("saveAttach parentId = {}, type = {}", parentId, type);
        log.info("saveAttach RequestPart = {}", attach);
        return commonAttachService.save(attach);
    }

    @PostMapping("/common/attach/{id}")
    public CommonAttach updateAttach(@PathVariable("id") Integer id, @RequestBody CommonAttach attach){

        log.info("updateAttach id = {}", id);
        log.info("updateAttach requestBody = {}", attach);

        return commonAttachService.update(id, attach);
    }

    @DeleteMapping("/common/attach/{id}")
    public Map deleteAttach(@PathVariable("id") Integer id){

        log.info("deleteAttach id = {}", id);
        Map<String, Object> response = new HashMap<>();
        Integer result = commonAttachService.deleteAttach(id);

        if (result > 0) {
            response.put("result", "OK");
        } else {
            response.put("result", "FAIL");
            response.put("message", "일치하는 정보를 찾을 수 없습니다.");
        }

        return response;
    }
}
