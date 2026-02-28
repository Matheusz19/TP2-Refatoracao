package TP2;

import TP2.dependencies.Project;
import TP2.dependencies.TestStatus;
import TP2.pipeline.PipelineResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PipelineTest {

    @Test
    void deveFazerDeployComSucesso_quandoTestesPassam() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(logger.getLoggedLines())
                .contains("INFO: Tests passed")
                .contains("INFO: Deployment successful");
    }

    @Test
    void naoDeveFazerDeploy_quandoTestesFalham() {
        var project = Project.builder()
                .setTestStatus(TestStatus.FAILING_TESTS)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(logger.getLoggedLines())
                .contains("ERROR: Tests failed")
                .doesNotContain("Deployment successful");
    }

    @Test
    void deveEnviarEmail_quandoConfigurado() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(true);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(emailer.messageSent)
                .isEqualTo("Deployment completed successfully");
    }

    @Test
    void naoDeveEnviarEmail_quandoDesabilitado() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(emailer.messageSent).isNull();
    }

    @Test
    void deveExecutarDeployMesmoSemTestes() {
        var project = Project.builder()
                .setTestStatus(TestStatus.NO_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(logger.getLoggedLines())
                .contains("INFO: No tests")
                .contains("INFO: Deployment successful");
    }

    @Test
    void deveLogarErro_quandoDeployFalha() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(logger.getLoggedLines())
                .contains("INFO: Tests passed")
                .contains("ERROR: Deployment failed");
    }

    @Test
    void deveEnviarEmailDeFalhaDeTestes() {
        var project = Project.builder()
                .setTestStatus(TestStatus.FAILING_TESTS)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(true);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(emailer.messageSent)
                .isEqualTo("Tests failed");
    }

    @Test
    void deveEnviarEmailDeFalhaDeDeploy() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(true);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(emailer.messageSent)
                .isEqualTo("Deployment failed");
    }

    @Test
    void deveLogarQuandoEmailEstaDesabilitado() {
        var project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        var logger = new CapturingLogger();
        var emailer = new FakeEmailer();
        var config = new FakeConfig(false);

        var pipeline = new Pipeline(config, emailer, logger);

        pipeline.run(project);

        assertThat(logger.getLoggedLines())
                .contains("INFO: Email disabled");
    }

    @Test
    void deveGerarMensagemCorreta_quandoTudoDaCerto() {
        var result = new PipelineResult(true, true);

        assertThat(result.getEmailMessage())
                .isEqualTo("Deployment completed successfully");
    }

    @Test
    void deveGerarMensagemDeFalhaDeDeploy() {
        var result = new PipelineResult(true, false);

        assertThat(result.getEmailMessage())
                .isEqualTo("Deployment failed");
    }

    @Test
    void deveGerarMensagemDeFalhaDeTestes() {
        var result = new PipelineResult(false, false);

        assertThat(result.getEmailMessage())
                .isEqualTo("Tests failed");
    }
}