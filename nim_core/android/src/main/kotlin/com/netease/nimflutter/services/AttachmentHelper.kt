/*
 * Copyright (c) 2021 NetEase, Inc.  All rights reserved.
 * Use of this source code is governed by a MIT license that can be
 * found in the LICENSE file.
 */

package com.netease.nimflutter.services

import com.netease.nimflutter.stringToNimNosSceneKeyConstant
import com.netease.nimflutter.toMap
import com.netease.nimflutter.update
import com.netease.nimlib.sdk.chatroom.model.*
import com.netease.nimlib.sdk.msg.attachment.*
import com.netease.nimlib.sdk.msg.constant.ChatRoomQueueChangeType
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum
import com.netease.nimlib.sdk.msg.constant.NotificationType
import com.netease.nimlib.sdk.team.model.*
import com.netease.yunxin.kit.alog.ALog
import org.json.JSONObject

object AttachmentHelper {

    fun attachmentFromMap(messageType: MsgTypeEnum, arguments: Map<String, *>): MsgAttachment? {
        val attachment = arguments.toMutableMap().apply {
            update("sen") { _, value ->
                stringToNimNosSceneKeyConstant(value as String?)
            }
        }
        return when (messageType) {
            MsgTypeEnum.file -> FileAttachment(JSONObject(attachment).toString())
            MsgTypeEnum.audio -> AudioAttachment(JSONObject(attachment).toString())
            MsgTypeEnum.video -> VideoAttachment(JSONObject(attachment).toString())
            MsgTypeEnum.image -> ImageAttachment(JSONObject(attachment).toString())
            MsgTypeEnum.location -> LocationAttachment(JSONObject(attachment).toString())
            MsgTypeEnum.custom -> CustomAttachment(arguments)
            else -> null
        }
    }

    fun attachmentToMap(messageType: MsgTypeEnum, attachment: MsgAttachment?): Map<String, Any?> =
        when (attachment) {
            is ImageAttachment -> attachment.toMap()
            is AudioAttachment -> attachment.toMap()
            is VideoAttachment -> attachment.toMap()
            is LocationAttachment -> attachment.toMap()
            is FileAttachment -> attachment.toMap()
            is ChatRoomRoomMemberInAttachment -> attachment.toMap()
            is ChatRoomTempMuteAddAttachment -> attachment.toMap()
            is ChatRoomTempMuteRemoveAttachment -> attachment.toMap()
            is ChatRoomQueueChangeAttachment -> attachment.toMap()
            is ChatRoomPartClearAttachment -> attachment.toMap()
            is ChatRoomNotificationAttachment -> attachment.toMap()
            is MuteMemberAttachment -> attachment.toMap()
            is MemberChangeAttachment -> attachment.toMap()
            is DismissAttachment -> attachment.toMap()
            is LeaveTeamAttachment -> attachment.toMap()
            is UpdateTeamAttachment -> attachment.toMap()
            else -> {
                if (messageType == MsgTypeEnum.custom) {
                    (attachment as? CustomAttachment)?.toMap() ?: mapOf()
                } else {
                    ALog.e("AttachmentHelper",
                        "message type $messageType with unknown attachment type: ${attachment?.javaClass?.name}")
                    mapOf()
                }
            }
        } + (if (attachment is NotificationAttachment) mapOf("type" to attachment.type.value) else emptyMap())


}