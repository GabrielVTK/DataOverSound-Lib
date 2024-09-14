package br.com.dataoversound.components.steps;

import br.com.dataoversound.models.ProtocolModel;

public class SetMessageStep implements Step {

    @Override
    public void encode(ProtocolModel protocolModel, String messageBits) {
        protocolModel.setMessage(messageBits);
    }

    @Override
    public void decode(ProtocolModel protocolModel, String messageBits) {
        String symbolsNumberBits = protocolModel.getNumberSymbol();
        int symbolsNumberLenght = protocolModel.getNumberSymbol().length();

        int symbolsNumber = Integer.parseInt(symbolsNumberBits, 2);
        int messageBitsLenght = symbolsNumber * 2;

        protocolModel.setMessage(messageBits.substring(symbolsNumberLenght, symbolsNumberLenght + messageBitsLenght));
    }

    @Override
    public void validate(ProtocolModel protocolModel) {
    }
}
