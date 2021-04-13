package com.bordozer.flux.repository;

import com.bordozer.flux.entity.ProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfileRepository extends ReactiveCrudRepository<ProfileEntity, Long> {

}
