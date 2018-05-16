/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.io.InputStream;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.server.inter.FlowManagerServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：流程管理业务实现
 */
@Service("flowManagerServer")
public class FlowManagerServerImpl implements FlowManagerServerI {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private ProcessEngine processEngine;

	@Autowired
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	public Pager<Model> findPager(String modelName, Integer page, Integer rows) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ModelQuery modelQuery = repositoryService.createModelQuery();
		if (!StringUtils.isBlank(modelName)) {
			modelQuery.modelNameLike(modelName);
		}
		List<Model> list = modelQuery.listPage((page - 1) * rows, rows);
		int total = (int) modelQuery.count();
		Pager<Model> dataGrid = new Pager<Model>(list, total);
		return dataGrid;
	}

	@Override
	public Json createFlow(String name, String key, String category, String description, InputStream inputStream) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Model modelData = repositoryService.newModel();
			// model的基本信息
			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));
			modelData.setCategory(category);

			ObjectNode editorNode = null;// 流程的json文件信息 或者bpmn文件json
			if (null == inputStream) {// 如果没有导入bpmn文件 构造简单的流程json定义文件
				editorNode = objectMapper.createObjectNode();
				editorNode.put("id", "canvas");
				editorNode.put("resourceId", "canvas");
				ObjectNode stencilSetNode = objectMapper.createObjectNode();
				stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
				editorNode.set("stencilset", stencilSetNode);
			} else {// 如果有导入bpmn文件 则将bpmn文件转为json格式存入act_ge_bytearray表
				BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
				XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
				XMLStreamReader xmlreader = xmlFactory.createXMLStreamReader(inputStream, "UTF-8");
				BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlreader);
				BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
				editorNode = bpmnJsonConverter.convertToJson(bpmnModel);
			}

			repositoryService.saveModel(modelData);// 存入modeler act_re_model
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("UTF-8"));// 将流程文件转为json格式存入act_ge_bytearray表
			return new Json(true, "创建模型成功！请编辑模型！");
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
			return new Json(false, "创建模型失败！");
		}
	}

	@Override
	public Json deployFlow(String modelId) {
		try {
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model,"UTF-8");
			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
			if (deployment != null) {
				return new Json(true, "流程部署成功！");
			} else {
				

				return new Json(false, "流程部署失败！");
			}
		} catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
			return new Json(false, "流程部署失败！");
		}
	}

	@Override
	public Json deleteFlow(String modelId) {
		try {
			RepositoryService repositoryService = processEngine.getRepositoryService();
			repositoryService.deleteModel(modelId);
			return new Json(true, "删除模型成功！");
		} catch (Exception e) {
			return new Json(false, "删除模型失败！");
		}
	}

}
