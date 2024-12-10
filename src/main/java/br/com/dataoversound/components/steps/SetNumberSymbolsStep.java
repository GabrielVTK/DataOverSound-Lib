package br.com.dataoversound.components.steps;

import br.com.dataoversound.models.ProtocolModel;
import br.com.dataoversound.utils.Converters;

public class SetNumberSymbolsStep implements Step {

    @Override
    public void encode(ProtocolModel protocolModel, String messageBits) {
        protocolModel.setNumberSymbol(Converters.convertIntegerToBinary(messageBits.length() / 2));
    }

    @Override
    public void decode(ProtocolModel protocolModel, String messageBits) {
        protocolModel.setNumberSymbol(messageBits.substring(0, 8));
    }

    @Override
    public void validate(ProtocolModel protocolModel) throws Exception {
        // Número de simbolos
        String symbolsNumberBits = protocolModel.getNumberSymbol();
        int symbolsNumber = Integer.parseInt(symbolsNumberBits, 2);
        int bitsNumber = symbolsNumber * 2;

        // Valida número de simbolos
        if (bitsNumber > protocolModel.getMessage().length()) {
            throw new Exception("Erro na demodulação da mensagem, número de bits insuficiente para mensagem");
        }
    }
}
