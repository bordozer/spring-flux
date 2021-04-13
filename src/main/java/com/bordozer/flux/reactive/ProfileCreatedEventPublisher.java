package com.bordozer.flux.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ProfileCreatedEventPublisher implements ApplicationListener<ProfileCreatedEvent>, Consumer<FluxSink<ProfileCreatedEvent>> {

    private final Executor executor;
    private final BlockingQueue<ProfileCreatedEvent> queue = new LinkedBlockingQueue<>();

    @Override
    public void accept(final FluxSink<ProfileCreatedEvent> sink) {
        executor.execute(() -> {
            while (true)
                try {
                    final ProfileCreatedEvent event = queue.take();
                    sink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }

    @Override
    public void onApplicationEvent(final ProfileCreatedEvent event) {
        queue.offer(event);
    }
}
