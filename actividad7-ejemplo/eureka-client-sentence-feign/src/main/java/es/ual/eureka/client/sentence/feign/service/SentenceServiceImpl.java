package es.ual.eureka.client.sentence.feign.service;

import es.ual.eureka.client.sentence.feign.client.ComplClient;
import es.ual.eureka.client.sentence.feign.client.SubjectClient;
import es.ual.eureka.client.sentence.feign.client.VerbClient;
import org.springframework.stereotype.Service;

@Service
public class SentenceServiceImpl implements SentenceService {

    private final SubjectClient subjectClient;
    private final VerbClient verbClient;
    private final ComplClient complClient;

    public SentenceServiceImpl(SubjectClient subjectClient, VerbClient verbClient, ComplClient complClient) {
        this.subjectClient = subjectClient;
        this.verbClient = verbClient;
        this.complClient = complClient;
    }

    @Override
    public String getSentence() {
        return subjectClient.getWord() + " " + verbClient.getWord() + " " + complClient.getWord();
    }
}