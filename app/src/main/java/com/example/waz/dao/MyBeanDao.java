package com.example.waz.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.waz.UI.Bean.MyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_BEAN".
*/
public class MyBeanDao extends AbstractDao<MyBean, Long> {

    public static final String TABLENAME = "MY_BEAN";

    /**
     * Properties of entity MyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Lid = new Property(0, Long.class, "lid", true, "_id");
        public final static Property AuthorName = new Property(1, String.class, "authorName", false, "AUTHOR_NAME");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Desc = new Property(3, String.class, "desc", false, "DESC");
        public final static Property Img = new Property(4, String.class, "img", false, "IMG");
        public final static Property NiceDate = new Property(5, String.class, "niceDate", false, "NICE_DATE");
        public final static Property SuperName = new Property(6, String.class, "superName", false, "SUPER_NAME");
        public final static Property Link = new Property(7, String.class, "link", false, "LINK");
        public final static Property IsChecked = new Property(8, boolean.class, "isChecked", false, "IS_CHECKED");
    }


    public MyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: lid
                "\"AUTHOR_NAME\" TEXT," + // 1: authorName
                "\"TITLE\" TEXT," + // 2: title
                "\"DESC\" TEXT," + // 3: desc
                "\"IMG\" TEXT," + // 4: img
                "\"NICE_DATE\" TEXT," + // 5: niceDate
                "\"SUPER_NAME\" TEXT," + // 6: superName
                "\"LINK\" TEXT," + // 7: link
                "\"IS_CHECKED\" INTEGER NOT NULL );"); // 8: isChecked
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyBean entity) {
        stmt.clearBindings();
 
        Long lid = entity.getLid();
        if (lid != null) {
            stmt.bindLong(1, lid);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(2, authorName);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(5, img);
        }
 
        String niceDate = entity.getNiceDate();
        if (niceDate != null) {
            stmt.bindString(6, niceDate);
        }
 
        String superName = entity.getSuperName();
        if (superName != null) {
            stmt.bindString(7, superName);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(8, link);
        }
        stmt.bindLong(9, entity.getIsChecked() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyBean entity) {
        stmt.clearBindings();
 
        Long lid = entity.getLid();
        if (lid != null) {
            stmt.bindLong(1, lid);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(2, authorName);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(5, img);
        }
 
        String niceDate = entity.getNiceDate();
        if (niceDate != null) {
            stmt.bindString(6, niceDate);
        }
 
        String superName = entity.getSuperName();
        if (superName != null) {
            stmt.bindString(7, superName);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(8, link);
        }
        stmt.bindLong(9, entity.getIsChecked() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyBean readEntity(Cursor cursor, int offset) {
        MyBean entity = new MyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // lid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // authorName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // desc
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // img
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // niceDate
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // superName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // link
            cursor.getShort(offset + 8) != 0 // isChecked
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyBean entity, int offset) {
        entity.setLid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAuthorName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDesc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImg(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNiceDate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSuperName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLink(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsChecked(cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyBean entity, long rowId) {
        entity.setLid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyBean entity) {
        if(entity != null) {
            return entity.getLid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyBean entity) {
        return entity.getLid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}