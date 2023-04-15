package zw.nmrl.onehealth.service.dto;

import java.util.ArrayList;
import java.util.List;

public class AnimalHealthDTO {

    private AnimalHealthDemographicDTO demographic;
    private AnimalHealthLaboratoryRequestDTO laboratoryRequest;
    private List<AnimalHealthAnalysisDTO> analysis = new ArrayList<AnimalHealthAnalysisDTO>();

    public AnimalHealthDemographicDTO getDemographic() {
        return demographic;
    }

    public void setDemographic(AnimalHealthDemographicDTO demographic) {
        this.demographic = demographic;
    }

    public AnimalHealthLaboratoryRequestDTO getLaboratoryRequest() {
        return laboratoryRequest;
    }

    public void setLaboratoryRequest(AnimalHealthLaboratoryRequestDTO laboratoryRequest) {
        this.laboratoryRequest = laboratoryRequest;
    }

    public List<AnimalHealthAnalysisDTO> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<AnimalHealthAnalysisDTO> analysis) {
        this.analysis = analysis;
    }
}
