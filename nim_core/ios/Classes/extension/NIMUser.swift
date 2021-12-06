/*
 * Copyright (c) 2021 NetEase, Inc.  All rights reserved.
 * Use of this source code is governed by a MIT license that can be
 * found in the LICENSE file.
 */

import Foundation
import NIMSDK

extension NIMUserInfo {
    @objc static func modelCustomPropertyMapper() -> [String : Any]? {
        return
            [#keyPath(NIMUserInfo.nickName):"nick",
             #keyPath(NIMUserInfo.avatarUrl):"avatar",
             #keyPath(NIMUserInfo.thumbAvatarUrl):"thumbAvatarUrl",
             #keyPath(NIMUserInfo.sign):"signature",
             #keyPath(NIMUserInfo.gender):"gender",
             #keyPath(NIMUserInfo.email):"email",
             #keyPath(NIMUserInfo.birth):"birthday",
             #keyPath(NIMUserInfo.mobile):"mobile",
             #keyPath(NIMUserInfo.ext):"extension",]
    }
}
