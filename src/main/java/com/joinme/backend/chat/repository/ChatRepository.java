package com.joinme.backend.chat.repository;

import com.joinme.backend.ratings.entity.Rating;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Rating, Long> {

}
