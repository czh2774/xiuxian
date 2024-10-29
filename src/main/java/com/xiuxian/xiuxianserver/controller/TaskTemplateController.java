package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.TaskTemplateDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.TaskTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskTemplateController类，处理任务模板的API请求。
 * 所有操作均使用POST请求。
 */


@RestController
@RequestMapping("/task/template")
public class TaskTemplateController {

    @Autowired
    private TaskTemplateService taskTemplateService;

    /**
     * 根据ID获取任务模板
     * @param request 请求体，包含任务模板ID
     * @return 任务模板DTO对象
     */
    @PostMapping("/getById")
    public ResponseEntity<TaskTemplateDTO> getTaskTemplateById(@RequestBody IdRequestDTO request) {
        TaskTemplateDTO taskTemplate = taskTemplateService.getTaskTemplateById(request.getId());
        return ResponseEntity.ok(taskTemplate);
    }

    /**
     * 获取所有任务模板
     * @return 所有任务模板DTO对象列表
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<TaskTemplateDTO>> getAllTaskTemplates() {
        List<TaskTemplateDTO> taskTemplates = taskTemplateService.getAllTaskTemplates();
        return ResponseEntity.ok(taskTemplates);
    }

    /**
     * 创建任务模板
     * @param request 创建任务模板的DTO请求体
     * @return 创建后的任务模板DTO对象
     */
    @PostMapping("/create")
    public ResponseEntity<TaskTemplateDTO> createTaskTemplate(@RequestBody TaskTemplateDTO request) {
        TaskTemplateDTO taskTemplate = taskTemplateService.createTaskTemplate(request);
        return ResponseEntity.ok(taskTemplate);
    }

    /**
     * 更新指定ID的任务模板
     * @param request 更新任务模板的DTO请求体，包含ID和更新内容
     * @return 更新后的任务模板DTO对象
     */
    @PostMapping("/update")
    public ResponseEntity<TaskTemplateDTO> updateTaskTemplate(@RequestBody TaskTemplateDTO request) {
        TaskTemplateDTO taskTemplate = taskTemplateService.updateTaskTemplate(request.getId(), request);
        return ResponseEntity.ok(taskTemplate);
    }

    /**
     * 删除指定ID的任务模板
     * @param request 请求体，包含任务模板ID
     * @return 空的响应体，状态为OK
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteTaskTemplate(@RequestBody IdRequestDTO request) {
        taskTemplateService.deleteTaskTemplate(request.getId());
        return ResponseEntity.ok().build();
    }
}
