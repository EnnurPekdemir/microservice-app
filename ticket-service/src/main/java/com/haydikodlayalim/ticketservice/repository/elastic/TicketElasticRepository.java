package com.haydikodlayalim.ticketservice.repository.elastic;

import com.haydikodlayalim.ticketservice.model.elastic.TicketModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketElasticRepository extends ElasticsearchRepository<TicketModel, String> {
}
