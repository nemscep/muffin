package com.nemscep.muffin.profile.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nemscep.muffin.profile.domain.entities.Currency
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.profile.domain.entities.Currency.RSD
import com.nemscep.muffin.profile.domain.entities.Currency.SWISS_FRANC
import com.nemscep.muffin.profile.domain.entities.Currency.US_DOLLAR
import com.nemscep.muffin.profile.domain.entities.Profile

@Entity(tableName = ProfileEntity.TABLE_NAME)
@TypeConverters(CurrencyConverter::class)
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "monthly_income")
    val monthlyIncome: Int,
    @ColumnInfo(name = "currency")
    val currency: Currency
) {
    companion object {
        const val TABLE_NAME = "profile"
    }
}

fun ProfileEntity.toDomain(): Profile =
    Profile(name = name, monthlyIncome = monthlyIncome, currency = currency)

fun Profile.toEntity(): ProfileEntity =
    ProfileEntity(name = name, monthlyIncome = monthlyIncome, currency = currency)

class CurrencyConverter {
    @TypeConverter
    fun fromJson(currency: String): Currency =
        when (currency) {
            "eur" -> EUR
            "usd" -> US_DOLLAR
            "swiss_franc" -> SWISS_FRANC
            "rsd" -> RSD
            else -> TODO()
        }

    @TypeConverter
    fun toJson(currency: Currency): String =
        when (currency) {
            EUR -> "eur"
            US_DOLLAR -> "usd"
            SWISS_FRANC -> "swiss_franc"
            RSD -> "rsd"
            else -> TODO()
        }
}
