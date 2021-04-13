package com.bordozer.flux.repository;

import com.bordozer.flux.entity.ProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProfileRepository extends ReactiveCrudRepository<ProfileEntity, Long> {

}
