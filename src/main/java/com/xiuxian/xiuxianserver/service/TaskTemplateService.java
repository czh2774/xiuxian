package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.TaskTemplateDTO;
import java.util.List;

/**
 * TaskTemplateService 接口，定义任务模板相关的业务逻辑接口
 */
public interface TaskTemplateService {

    /**
     * 获取所有任务模板的列表
     *
     * @return 包含所有任务模板DTO的列表
     */
    List<TaskTemplateDTO> getAllTaskTemplates();

    /**
     * 根据ID获取任务模板
     *
     * @param id 任务模板的唯一标识符
     * @return 对应的任务模板DTO
     */
    TaskTemplateDTO getTaskTemplateById(Long id);

    /**
     * 创建任务模板
     *
     * @param taskTemplateDTO 任务模板DTO对象
     * @return 创建后的任务模板DTO
     */
    TaskTemplateDTO createTaskTemplate(TaskTemplateDTO taskTemplateDTO);

    /**
     * 更新任务模板
     *
     * @param id 任务模板的唯一标识符
     * @param taskTemplateDTO 任务模板DTO对象
     * @return 更新后的任务模板DTO
     */
    TaskTemplateDTO updateTaskTemplate(Long id, TaskTemplateDTO taskTemplateDTO);

    /**
     * 删除任务模板
     *
     * @param id 任务模板的唯一标识符
     */
    void deleteTaskTemplate(Long id);
}
