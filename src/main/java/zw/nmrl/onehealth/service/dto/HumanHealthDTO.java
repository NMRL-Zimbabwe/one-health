package zw.nmrl.onehealth.service.dto;

import java.util.ArrayList;
import java.util.List;

public class HumanHealthDTO {

    private HumanHealthDemographicDTO demographic;
    private HumanHealthLaboratoryRequestDTO laboratoryRequest;
    private List<HumanHealthAnalysisDTO> analysis = new ArrayList<HumanHealthAnalysisDTO>();

    public HumanHealthDemographicDTO getDemographic() {
        return demographic;
    }

    public void setDemographic(HumanHealthDemographicDTO demographic) {
        this.demographic = demographic;
    }

    public HumanHealthLaboratoryRequestDTO getLaboratoryRequest() {
        return laboratoryRequest;
    }

    public void setLaboratoryRequest(HumanHealthLaboratoryRequestDTO laboratoryRequest) {
        this.laboratoryRequest = laboratoryRequest;
    }

    public List<HumanHealthAnalysisDTO> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<HumanHealthAnalysisDTO> analysis) {
        this.analysis = analysis;
    }
}
