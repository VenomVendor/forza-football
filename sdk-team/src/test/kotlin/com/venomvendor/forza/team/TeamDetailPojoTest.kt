/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team

import com.openpojo.reflection.impl.PojoClassFactory
import com.openpojo.validation.ValidatorBuilder
import com.openpojo.validation.rule.impl.GetterMustExistRule
import com.openpojo.validation.rule.impl.NoNestedClassRule
import com.openpojo.validation.rule.impl.SetterMustExistRule
import com.openpojo.validation.test.impl.DefaultValuesNullTester
import com.openpojo.validation.test.impl.GetterTester
import com.openpojo.validation.test.impl.SetterTester
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import org.junit.Test

/**
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class TeamDetailPojoTest {

    @Test
    fun searchTeamPojoTest() {
        val validator = ValidatorBuilder.create()
            .with(GetterMustExistRule())
            .with(SetterMustExistRule())
            .with(SetterTester())
            .with(GetterTester())
            .with(DefaultValuesNullTester())
            .with(NoNestedClassRule())
            // Build assertion criteria
            .build()

        // Add all classes
        val clzez = setOf(
            TeamDetail::class.java,
            Team::class.java
        )

        for (clazz in clzez) {
            // This is where the assertion happens
            validator.validate(PojoClassFactory.getPojoClass(clazz))
        }
    }
}
