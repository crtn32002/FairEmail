package eu.faircode.email;

/*
    This file is part of Safe email.

    Safe email is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    NetGuard is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with NetGuard.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2018 by Marcel Bokhorst (M66B)
*/

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = EntityFolder.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(childColumns = "account", entity = EntityAccount.class, parentColumns = "id", onDelete = CASCADE)
        },
        indices = {
                @Index(value = {"account", "name"}, unique = true),
                @Index(value = {"account"}),
                @Index(value = {"name"}),
                @Index(value = {"type"})
        }
)
public class EntityFolder implements Serializable {
    static final String TABLE_NAME = "folder";

    static final String INBOX = "Inbox";
    static final String OUTBOX = "Outbox";
    static final String ARCHIVE = "All";
    static final String DRAFTS = "Drafts";
    static final String TRASH = "Trash";
    static final String JUNK = "Junk";
    static final String SENT = "Sent";
    static final String USER = "User";

    static final List<String> SYSTEM_FOLDER_ATTR = Arrays.asList(
            "All",
            "Drafts",
            "Trash",
            "Junk",
            "Sent"
    );
    static final List<String> SYSTEM_FOLDER_TYPE = Arrays.asList(
            ARCHIVE,
            DRAFTS,
            TRASH,
            JUNK,
            SENT
    ); // MUST match SYSTEM_FOLDER_ATTR

    static final List<String> FOLDER_SORT_ORDER = Arrays.asList(
            INBOX,
            OUTBOX,
            DRAFTS,
            SENT,
            ARCHIVE,
            TRASH,
            JUNK,
            USER
    );

    static final int DEFAULT_INBOX_SYNC = 30; // days
    static final int DEFAULT_SYSTEM_SYNC = 7; // days
    static final int DEFAULT_USER_SYNC = 7; // days

    static final List<String> SYSTEM_FOLDER_SYNC = Arrays.asList(
            ARCHIVE,
            DRAFTS,
            TRASH,
            SENT
    );

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long account; // Outbox = null
    @NonNull
    public String name;
    @NonNull
    public String type;
    @NonNull
    public Boolean synchronize;
    @NonNull
    public Integer after; // days

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntityFolder) {
            EntityFolder other = (EntityFolder) obj;
            return ((this.account == null ? other.account == null : this.account.equals(other.account)) &&
                    this.name.equals(other.name) &&
                    this.type.equals(other.type) &&
                    this.synchronize.equals(other.synchronize) &&
                    this.after.equals(other.after));
        } else
            return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
