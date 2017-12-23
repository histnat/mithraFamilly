/**
 * 
 */
package net.mithra.familly.db.vo.common;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * @author frebeche
 *
 */
public class Common implements Serializable {
	

    /**
	 * 
	 */
	private static final long serialVersionUID = -349757551309249338L;
	@Id
    protected String id;


    public Common() {

    }

    public Common(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Common other = (Common) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
