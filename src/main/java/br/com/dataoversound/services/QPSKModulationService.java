package br.com.dataoversound.services;

import br.com.dataoversound.components.ProtocolComponent;
import br.com.dataoversound.components.QPSKModulationComponent;
import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.Converters;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QPSKModulationService {

    private QPSKModulationComponent qpskModulationComponent;

    private ProtocolComponent protocolComponent;

    public QPSKModulationService(QPSKParameters parameters) {
        this.qpskModulationComponent = new QPSKModulationComponent(parameters);
        this.protocolComponent = new ProtocolComponent();
    }

    public double[] modulateMessage(String message) {
        String messageBits = Converters.convertStringToBinary(message);

        String protocol = this.protocolComponent.encodeProtocol(messageBits);

        return this.qpskModulationComponent.modulateBits(protocol);
    }

}
