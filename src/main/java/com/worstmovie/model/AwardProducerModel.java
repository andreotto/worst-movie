package com.worstmovie.model;

import java.util.List;

public record AwardProducerModel(List<IntervalInfo> min, List<IntervalInfo> max) {
}
