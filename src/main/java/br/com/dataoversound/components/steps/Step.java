package br.com.dataoversound.components.steps;

import br.com.dataoversound.models.ProtocolModel;

public interface Step {

    public void encode(ProtocolModel protocolModel, String messageBits);

    public void decode(ProtocolModel protocolModel, String messageBits);

    public void validate(ProtocolModel protocolModel) throws Exception;

}
