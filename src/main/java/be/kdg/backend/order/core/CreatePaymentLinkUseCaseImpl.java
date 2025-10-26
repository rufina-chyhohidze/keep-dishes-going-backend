package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.CreatePaymentLinkUseCase;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.PaymentPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class CreatePaymentLinkUseCaseImpl implements CreatePaymentLinkUseCase {
    private final LoadOrderPort loadOrderPort;
    private final PaymentPort paymentPort;

    public CreatePaymentLinkUseCaseImpl(LoadOrderPort loadOrderPort, PaymentPort paymentPort) {
        this.loadOrderPort = loadOrderPort;
        this.paymentPort = paymentPort;
    }

    @Override
    public String createPaymentLink(UUID orderId) {
        Order order = loadOrderPort.load(orderId);
        BigDecimal total = BigDecimal.valueOf(order.calculateTotalPrice());
        return paymentPort.createPaymentLink(orderId, total);
    }
}
