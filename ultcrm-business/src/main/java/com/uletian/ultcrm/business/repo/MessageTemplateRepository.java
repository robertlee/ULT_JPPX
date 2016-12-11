package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.MessageTemplate;

@RepositoryRestResource(collectionResourceRel = "messageTemplate", path = "messageTemplate")
public interface MessageTemplateRepository  extends  PagingAndSortingRepository<MessageTemplate, Long>{
	MessageTemplate findByCode(String code);
}
