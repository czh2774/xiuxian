package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.TaskTemplateDTO;
import com.xiuxian.xiuxianserver.entity.TaskTemplate;
import com.xiuxian.xiuxianserver.entity.TaskReward;
import com.xiuxian.xiuxianserver.exception.CustomConversionException;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.TaskTemplateRepository;
import com.xiuxian.xiuxianserver.service.TaskTemplateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskTemplateServiceImpl 实现了任务模板相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class TaskTemplateServiceImpl implements TaskTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(TaskTemplateServiceImpl.class);

    private final TaskTemplateRepository taskTemplateRepository;

    /**
     * 获取所有任务模板的列表。
     *
     * @return 包含所有任务模板DTO的列表
     */
    @Override
    public List<TaskTemplateDTO> getAllTaskTemplates() {
        logger.info("开始获取所有任务模板");

        List<TaskTemplate> templates = taskTemplateRepository.findAll();

        // 将实体列表转换为DTO列表
        List<TaskTemplateDTO> templateDTOs = templates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("成功获取所有任务模板，总计: {} 个", templateDTOs.size());

        return templateDTOs;
    }

    /**
     * 根据ID获取任务模板。
     *
     * @param id 任务模板的唯一标识符
     * @return 对应的任务模板DTO
     * @throws ResourceNotFoundException 如果任务模板未找到
     */
    @Override
    public TaskTemplateDTO getTaskTemplateById(Long id) {
        logger.info("根据ID获取任务模板，ID: {}", id);

        TaskTemplate template = taskTemplateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("未找到任务模板，ID: {}", id);
                    return new ResourceNotFoundException("任务模板未找到，ID: " + id);
                });

        logger.info("成功获取任务模板，ID: {}", id);

        // 将实体转换为DTO
        return convertToDTO(template);
    }

    /**
     * 创建任务模板。
     *
     * @param taskTemplateDTO 任务模板DTO对象
     * @return 创建后的任务模板DTO对象
     */
    @Override
    public TaskTemplateDTO createTaskTemplate(TaskTemplateDTO taskTemplateDTO) {
        logger.info("开始创建新的任务模板");

        TaskTemplate taskTemplate = convertToEntity(taskTemplateDTO);
        taskTemplate = taskTemplateRepository.save(taskTemplate);

        logger.info("成功创建任务模板，ID: {}", taskTemplate.getId());

        return convertToDTO(taskTemplate);
    }

    /**
     * 更新指定ID的任务模板。
     *
     * @param id 任务模板的唯一标识符
     * @param taskTemplateDTO 任务模板DTO对象
     * @return 更新后的任务模板DTO对象
     */
    @Override
    public TaskTemplateDTO updateTaskTemplate(Long id, TaskTemplateDTO taskTemplateDTO) {
        logger.info("开始更新任务模板，ID: {}", id);

        TaskTemplate existingTemplate = taskTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("任务模板未找到，ID: " + id));

        TaskTemplate updatedTemplate = convertToEntity(taskTemplateDTO);
        updatedTemplate.setId(existingTemplate.getId()); // 保持ID一致

        taskTemplateRepository.save(updatedTemplate);

        logger.info("成功更新任务模板，ID: {}", updatedTemplate.getId());

        return convertToDTO(updatedTemplate);
    }

    /**
     * 删除指定ID的任务模板。
     *
     * @param id 任务模板的唯一标识符
     */
    @Override
    public void deleteTaskTemplate(Long id) {
        logger.info("开始删除任务模板，ID: {}", id);

        if (!taskTemplateRepository.existsById(id)) {
            throw new ResourceNotFoundException("任务模板未找到，ID: " + id);
        }

        taskTemplateRepository.deleteById(id);

        logger.info("成功删除任务模板，ID: {}", id);
    }

    /**
     * 将 TaskTemplate 实体转换为 DTO。
     *
     * @param template 实体对象
     * @return DTO对象
     */
    private TaskTemplateDTO convertToDTO(TaskTemplate template) {
        return new TaskTemplateDTO(
                template.getId(),
                template.getName(),
                template.getDescription(),
                template.getType().toString(),
                template.getGoalType().toString(),
                template.getGoalValue(),
                template.getRewards().stream()
                        .map(reward -> new TaskTemplateDTO.TaskRewardDTO(reward.getType().toString(), reward.getValue()))
                        .collect(Collectors.toList()),
                template.getPreTaskId(),
                template.getRequiredPlayerLevel(),
                template.getStatus().toString(),
                template.isTriggerNextTask()
        );
    }

    private TaskTemplate convertToEntity(TaskTemplateDTO dto) {
        try {
            return TaskTemplate.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .type(TaskTemplate.TaskType.valueOf(dto.getType()))
                    .goalType(TaskTemplate.TaskGoalType.valueOf(dto.getGoalType()))
                    .goalValue(dto.getGoalValue())
                    .rewards(dto.getRewards().stream()
                            .map(rewardDTO -> {
                                TaskTemplate.Reward reward = new TaskTemplate.Reward(); // 使用 TaskTemplate.Reward
                                reward.setType(TaskTemplate.RewardType.valueOf(rewardDTO.getType()));
                                reward.setValue(rewardDTO.getValue());
                                return reward;
                            })
                            .collect(Collectors.toList()))
                    .preTaskId(dto.getPreTaskId())
                    .requiredPlayerLevel(dto.getRequiredPlayerLevel())
                    .status(TaskTemplate.TaskStatus.valueOf(dto.getStatus()))
                    .triggerNextTask(dto.isTriggerNextTask())
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle conversion error
            throw new CustomConversionException("Error converting TaskTemplateDTO to TaskTemplate", e);
        }
    }


}
