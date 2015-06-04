package com.mlinc.mlplanets.transport;

public class PredictionSummaryDTO {

    private Long periodosDeSequia;

    private Long periodosDeLluvia;

    private Long periodosDeClimaOptimo;

    private Long diasDeMaximasLluvias[];

    public long getPeriodosDeSequia() {
        return periodosDeSequia;
    }

    public void setPeriodosDeSequia(Long periodosDeSequia) {
        this.periodosDeSequia = periodosDeSequia;
    }

    public Long getPeriodosDeLluvia() {
        return periodosDeLluvia;
    }

    public void setPeriodosDeLluvia(Long periodosDeLluvia) {
        this.periodosDeLluvia = periodosDeLluvia;
    }

    public Long getPeriodosDeClimaOptimo() {
        return periodosDeClimaOptimo;
    }

    public void setPeriodosDeClimaOptimo(Long periodosDeClimaOptimo) {
        this.periodosDeClimaOptimo = periodosDeClimaOptimo;
    }

    public Long[] getDiasDeMaximasLluvias() {
        return diasDeMaximasLluvias;
    }

    public void setDiasDeMaximasLluvias(Long[] diasDeMaximasLluvias) {
        this.diasDeMaximasLluvias = diasDeMaximasLluvias;
    }
}
