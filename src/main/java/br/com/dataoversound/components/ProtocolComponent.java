package br.com.dataoversound.components;

import br.com.dataoversound.components.steps.SetMessageStep;
import br.com.dataoversound.components.steps.SetNumberSymbolsStep;
import br.com.dataoversound.components.steps.Step;
import br.com.dataoversound.components.steps.StepParityBitComponent;
import br.com.dataoversound.models.ProtocolModel;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ProtocolComponent {

    private List<Step> steps;

    public ProtocolComponent() {
        this.populateSteps();
    }

    private void populateSteps() {
        this.steps = new ArrayList<>();
        this.steps.add(new SetNumberSymbolsStep());
        this.steps.add(new SetMessageStep());
        this.steps.add(new StepParityBitComponent());
    }

    public String encodeProtocol(String messageBits) {
        ProtocolModel protocolModel = new ProtocolModel();
        for (Step step : this.steps) {
            step.encode(protocolModel, messageBits);
        }
        System.out.println("encodeProtocol: " + protocolModel);

        return protocolModel.getProtocolBits();
    }

    public String decodeProtocol(String messageBits) throws Exception {
        ProtocolModel protocolModel = new ProtocolModel();
        for (Step step : this.steps) {
            step.decode(protocolModel, messageBits);
        }
        System.out.println("decodeProtocol: " + protocolModel);

        this.validateProtocol(protocolModel);

        return protocolModel.getMessage();
    }

    private void validateProtocol(ProtocolModel protocolModel) throws Exception {
        for (Step step : this.steps) {
            step.validate(protocolModel);
        }
    }

}
