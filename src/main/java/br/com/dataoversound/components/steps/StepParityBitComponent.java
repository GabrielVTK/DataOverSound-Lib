package br.com.dataoversound.components.steps;

import br.com.dataoversound.components.error.detection.ParityBitComponent;
import br.com.dataoversound.models.ProtocolModel;

public class StepParityBitComponent implements Step {

    ParityBitComponent parityBitComponent = new ParityBitComponent();

    @Override
    public void encode(ProtocolModel protocolModel, String messageBits) {
        protocolModel.setParityBit(this.parityBitComponent.calculate(messageBits));
    }

    @Override
    public void decode(ProtocolModel protocolModel, String messageBits) {
        int numberSymbolLenght = protocolModel.getNumberSymbol().length();
        int messageLenght = protocolModel.getMessage().length();
        int index = numberSymbolLenght + messageLenght;

        protocolModel.setParityBit(messageBits.substring(index, index + 2));
    }

    @Override
    public void validate(ProtocolModel protocolModel) throws Exception {
        if (!this.parityBitComponent.validate(protocolModel.getMessage(), protocolModel.getParityBit())) {
            throw new Exception("Bit de Paridade Inválido");
        }
    }

}
