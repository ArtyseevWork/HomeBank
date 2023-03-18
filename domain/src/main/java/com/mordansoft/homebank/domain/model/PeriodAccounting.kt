package com.mordansoft.homebank.domain.model

data class PeriodAccounting(var capitalFact         : Float = 0F,
                            var capitalPlan         : Float = 0F,
                            var previousCapitalFact : Float = 0F,
                            var previousCapitalPlan : Float = 0F,
                            var balancePlan         : Float = 0F,
                            var balanceFact         : Float = 0F,
                            var expencesPlan        : Float = 0F,
                            var expencesFact        : Float = 0F)
