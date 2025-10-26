package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.ConfirmOrderPaymentUseCase;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ConfirmOrderPaymentUseCaseImpl implements ConfirmOrderPaymentUseCase {

    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;

    public ConfirmOrderPaymentUseCaseImpl(LoadOrderPort loadOrderPort, SaveOrderPort saveOrderPort) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
    }

    @Override
    public void confirmPayment(UUID orderId) {
        Order order = loadOrderPort.load(orderId);
        order.getPaymentInfo().markAsCompleted();
        saveOrderPort.save(order);
    }
}
