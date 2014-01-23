package com.votingcentral.util.xml;

import java.io.InputStream;


public interface   VelocityResolver {
    long getLastModified(String name)
            throws org.apache.velocity.exception.ResourceNotFoundException ;

    InputStream getResource(String name)
        throws org.apache.velocity.exception.ResourceNotFoundException ;

}
