/*
 * Copyright (C) 2017-2018 Trillium Inc. <support@trilliumsecure.com>
 */
package jp.co.trillium.secureskye.vehicle.message.model

import jp.co.trillium.secureskye.common.util.Timestamps
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

/**
 * An event that reports the total driven kilometers of a vehicle.
 *
 * @property id The unique identifier of this event.
 * @property vehicleId The unique identifier of vehicle that produced this event.
 * @property timestamp The timestamp when this event occurred.
 * @property systemTimestamp The timestamp when this event was saved on the server.
 * @property value The total kilometers of the vehicle.
 */
@Document(collection = "vehicles.events.odometer")
@CompoundIndexes(
    CompoundIndex(name = "event_id", def = "{'vehicleId': 1, 'systemTimestamp.dt': -1, 'systemTimestamp.ns': -1}"),
    CompoundIndex(name = "timestamp", def = "{'timestamp.dt': -1, 'timestamp.ns': -1}"),
    CompoundIndex(name = "systemTimestamp", def = "{'systemTimestamp.dt': -1, 'systemTimestamp.ns': -1}")
)
data class OdometerEvent(
    @Id
    override var id: ObjectId = ObjectId(),
    @Indexed
    override var vehicleId: UUID = UUID(0, 0),
    override var timestamp: LocalDateTime = Timestamps.nowTime(),
    override var systemTimestamp: LocalDateTime = Timestamps.nowTime(),

    var value: Long = 0
) : EventEntity