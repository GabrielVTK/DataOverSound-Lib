package br.com.dataoversound.services;

import br.com.dataoversound.components.ProtocolComponent;
import br.com.dataoversound.components.QPSKDemodulationComponent;
import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.Converters;

public class QPSKDemodulationService {

    private QPSKDemodulationComponent qpskDemodulationComponent;

    private ProtocolComponent protocolComponent;

    public QPSKDemodulationService(QPSKParameters parameters) {
        this.qpskDemodulationComponent = new QPSKDemodulationComponent(parameters);
        this.protocolComponent = new ProtocolComponent();
    }

    public String demodulateMessage(double[] signal) throws Exception {
        String bitsSignal = this.qpskDemodulationComponent.signalToBits(signal);

        String messageBits = protocolComponent.decodeProtocol(bitsSignal);

        return Converters.convertBinaryToString(messageBits);
    }

}