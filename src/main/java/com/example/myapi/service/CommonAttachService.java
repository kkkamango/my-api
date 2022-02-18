package com.example.myapi.service;

import com.example.myapi.model.Board;
import com.example.myapi.model.CommonAttach;
import com.example.myapi.model.CommonAttachType;
import com.example.myapi.repository.BoardRepository;
import com.example.myapi.repository.CommonAttachRepository;
import com.example.myapi.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommonAttachService {

    private CommonAttachRepository commonAttachRepository;

    @Autowired
    public CommonAttachService(CommonAttachRepository commonAttachRepository) {
        this.commonAttachRepository = commonAttachRepository;
    }

    public CommonAttach getCommonAttach(Integer id){
        return commonAttachRepository.findByIdAndDelYn(id, "N").orElseGet(CommonAttach::new);
    }

    public List<CommonAttach> getCommonAttachList(Integer parentId, CommonAttachType type){
        return commonAttachRepository.findByParentIdAndTypeAndDelYn(parentId, type.name(), "N");
    }

    @Transactional
    public CommonAttach save(CommonAttach attach) {
        log.info("파일을 업로드 합니다. attach = {}", attach);
        CommonAttach uploadFile = FileUploadUtil.upload(attach.getFile(), attach.getType());

        if (uploadFile == null){
//            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        uploadFile.setParentId(attach.getParentId());
        uploadFile.setType(attach.getType());

        log.info("DB에 저장 합니다. uploadFile = {}", uploadFile);
        CommonAttach savedAttach = commonAttachRepository.save(uploadFile);

        this.updateParent(savedAttach);
        return savedAttach;
    }

    private void updateParent(CommonAttach attach) {

        switch (attach.getType()){
            case BOARD_IMG_MAIN:

            break;
        }
    }

    public List<CommonAttach> saveAll(List<CommonAttach> attachList){
        return commonAttachRepository.saveAll(attachList);
    }

    public CommonAttach update(int id, CommonAttach attach) {

        attach.setId(id);
        attach.setDelYn("N");
        return this.save(attach);
    }

    @Transactional
    public Integer deleteAttach(Integer id) {

        Optional<CommonAttach> optionalAttach = commonAttachRepository.findByIdAndDelYn(id, "N");

        if (optionalAttach.isPresent()){
            CommonAttach attach = optionalAttach.get();
            attach.setDelYn("Y");
            commonAttachRepository.save(attach);
            return attach.getId();
        }
        return 0;
    }

}
