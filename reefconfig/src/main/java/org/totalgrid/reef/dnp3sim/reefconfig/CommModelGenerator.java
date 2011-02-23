package org.totalgrid.reef.dnp3sim.reefconfig;

import org.totalgrid.reef.loader.communications.CommunicationsModel;
import org.totalgrid.reef.loader.communications.Endpoint;

import java.util.LinkedList;
import java.util.List;


public class CommModelGenerator {
    private List<EndpointGenerator> endpointGenerators;

    public CommModelGenerator(List<EndpointGenerator> endpointGenerators) {
        this.endpointGenerators = endpointGenerators;
    }

    public CommunicationsModel model() {
        CommunicationsModel model = new CommunicationsModel();

        for (EndpointGenerator generator : endpointGenerators) {
            model.getEndpoint().addAll(generator.allEndpoints());
        }

        return model;
    }
}
