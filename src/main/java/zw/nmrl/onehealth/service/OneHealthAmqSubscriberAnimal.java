package zw.nmrl.onehealth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zw.nmrl.onehealth.domain.LaboratoryRequest;
import zw.nmrl.onehealth.service.dto.AnimalHealthDTO;

@Service
//@Transactional
public class OneHealthAmqSubscriberAnimal {

    /**
     * @throws TimeoutException
     * @throws IOException
     * @date 08-10-2020
     * @writer Lawrence
     */

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange("amq.direct"), key = "health", value = @Queue("oneHealth")))
    public void reciveRequestFromLIMS(Message msg) throws IOException, TimeoutException {
        String string = new String(msg.getBody());
        msg.getMessageProperties().getHeader("request");

        System.out.println("Received Message: " + msg.getBody());
        // RegistrationFromLims obj = mapper.readValue(string,
        // RegistrationFromLims.class);

        /*
        @NotNull
        @Column(name = "sample_id", nullable = false)
        private String sampleId;

        @NotNull
        @Column(name = "sample_type_id", nullable = false)
        private String sampleTypeId;

        @Column(name = "date_collected")
        private LocalDate dateCollected;

        @Column(name = "date_received")
        private LocalDate dateReceived;

        @Column(name = "sample_condition")
        private String sampleCondition;

        @Column(name = "client_id")
        private String clientId;

        @Column(name = "priority")
        private Integer priority;

        @Column(name = "status")
        private String status;

        @Column(name = "remarks")
        private String remarks;

        @Column(name = "location_id")
        private String locationId;

        @Column(name = "sector_id")
        private String sectorId;

        @Column(name = "district_id")
        private String districtId;

        @Column(name = "procince_id")
        private String procinceId;*/

        AnimalHealthDTO ahDTO = mapper.readValue(string, AnimalHealthDTO.class);
        // laboratoryRequestService.updateLaoratoryRequest(obj);

        LaboratoryRequest lb = new LaboratoryRequest();
        lb.setSampleId(ahDTO.getLaboratoryRequest().getSampleId());
        //lb.setDateCollected(LocalDate.parse(ahDTO.getLaboratoryRequest().getFirstSubmissionDate());
        lb.setDateReceived(LocalDate.parse(ahDTO.getLaboratoryRequest().getSubmissionDate()));
        //lb.setSampleCondition(ahDTO.getLaboratoryRequest().sa;
        //lb.setSampleTypeId(ahDTO.getLaboratoryRequest().

        //ahDTO.getLaboratoryRequest();

        /*  "sampleId":"BP23-1456",
        * "farmingSystem": "intensive",
        "dipTank": { "longitude": 36567.8, "latitude": 8689.6 },
        "isFollowUpSubmission": "true",
        "firstSubmissionDate": "2023-03-12",
        "numberOfSpecimen": "120",
        "specimenDescription": "sealed",
        "testRequired": "test name, test code",
        "purposeOfTest": "outbreak",
        "clinicalHistory": "BP",
        "clinicalSigns": "fever",
        "postMortemFindings": "poisoned",
        "tentativeDiagnosis": "foot and mouth",
        "totalNumberOfAnimals": "23",
        "numberOfRiskAnimals": "12",
        "numberOfDeadAnimals": "24",
        "numberOfSickAnimals": "10",
        "examinationRequired": "toxicology",
        "submissionDate": "2023-04-12",
        "receivedBy": "Allan Chadamoyo",
        "receiverTitle": "Dr",
        "specimenBoxNumber": "4",
        "deathDate": "2023-04-14",
        "deathTime": "12:00",
        "vaccinationDate": "2023-02-01",
        "vaccinationAge": "2",
        "vaccinationContacts": "vet",
        "mannerOfDeath": "killed",
        "whoDestroyed": "Arthur Musendame"
        */

        //laboratoryRequestService.save(lb);
    }
}
