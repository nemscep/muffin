package com.nemscep.muffin.balances.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nemscep.muffin.balances.data.models.BalanceEntityType.MAIN
import com.nemscep.muffin.balances.data.models.BalanceEntityType.SAVINGS
import com.nemscep.muffin.balances.data.models.BalanceEntityType.SPECIFIC
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.profile.domain.entities.Currency

@Entity(tableName = BalanceEntity.TABLE_NAME)
data class BalanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: Float,
    val currency: Currency,
    val name: String? = null,
    val type: BalanceEntityType,
    val isVisibleInOverview: Boolean
) {
    companion object {
        const val TABLE_NAME = "balance"
    }
}

enum class BalanceEntityType {
    MAIN,
    SAVINGS,
    SPECIFIC
}

fun Balance.toEntity(): BalanceEntity = when (this) {
    is MainBalance -> BalanceEntity(
        value = value,
        currency = currency,
        type = MAIN,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    is SavingsBalance -> BalanceEntity(
        value = value,
        currency = currency,
        type = SAVINGS,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    is SpecificBalance -> BalanceEntity(
        value = value,
        currency = currency,
        name = name,
        type = SPECIFIC,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
}

fun BalanceEntity.toDomain(): Balance = when (this.type) {
    MAIN -> MainBalance(
        value = value,
        currency = currency,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    SAVINGS -> SavingsBalance(
        value = value,
        currency = currency,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    SPECIFIC -> SpecificBalance(
        value = value,
        name = name!!,
        currency = currency,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
}

