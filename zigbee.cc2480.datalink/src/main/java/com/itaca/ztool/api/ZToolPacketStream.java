/*
   Copyright 2008-2010 Andrew Rapp, http://code.google.com/p/xbee-api/

   Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.itaca.ztool.api;

import java.io.IOException;
import java.io.InputStream;

import com.itaca.ztool.api.af.AF_DATA_CONFIRM;
import com.itaca.ztool.api.af.AF_DATA_SRSP;
import com.itaca.ztool.api.af.AF_INCOMING_MSG;
import com.itaca.ztool.api.af.AF_REGISTER_SRSP;
import com.itaca.ztool.api.simple.ZB_ALLOW_BIND_CONFIRM;
import com.itaca.ztool.api.simple.ZB_ALLOW_BIND_RSP;
import com.itaca.ztool.api.simple.ZB_APP_REGISTER_RSP;
import com.itaca.ztool.api.simple.ZB_BIND_CONFIRM;
import com.itaca.ztool.api.simple.ZB_BIND_DEVICE_RSP;
import com.itaca.ztool.api.simple.ZB_FIND_DEVICE_CONFIRM;
import com.itaca.ztool.api.simple.ZB_FIND_DEVICE_REQUEST_RSP;
import com.itaca.ztool.api.simple.ZB_GET_DEVICE_INFO_RSP;
import com.itaca.ztool.api.simple.ZB_PERMIT_JOINING_REQUEST_RSP;
import com.itaca.ztool.api.simple.ZB_READ_CONFIGURATION_RSP;
import com.itaca.ztool.api.simple.ZB_RECEIVE_DATA_INDICATION;
import com.itaca.ztool.api.simple.ZB_SEND_DATA_CONFIRM;
import com.itaca.ztool.api.simple.ZB_SEND_DATA_REQUEST_RSP;
import com.itaca.ztool.api.simple.ZB_START_CONFIRM;
import com.itaca.ztool.api.simple.ZB_START_REQUEST_RSP;
import com.itaca.ztool.api.simple.ZB_WRITE_CONFIGURATION_RSP;
import com.itaca.ztool.api.system.SYS_ADC_READ_SRSP;
import com.itaca.ztool.api.system.SYS_GPIO_SRSP;
import com.itaca.ztool.api.system.SYS_OSAL_NV_READ_SRSP;
import com.itaca.ztool.api.system.SYS_OSAL_NV_WRITE_SRSP;
import com.itaca.ztool.api.system.SYS_OSAL_START_TIMER_SRSP;
import com.itaca.ztool.api.system.SYS_OSAL_STOP_TIMER_SRSP;
import com.itaca.ztool.api.system.SYS_OSAL_TIMER_EXPIRED_IND;
import com.itaca.ztool.api.system.SYS_PING_RESPONSE;
import com.itaca.ztool.api.system.SYS_RANDOM_SRSP;
import com.itaca.ztool.api.system.SYS_RESET_RESPONSE;
import com.itaca.ztool.api.system.SYS_RPC_ERROR;
import com.itaca.ztool.api.system.SYS_TEST_LOOPBACK_SRSP;
import com.itaca.ztool.api.system.SYS_VERSION_RESPONSE;
import com.itaca.ztool.api.util.UTIL_SET_CHANNELS_RESPONSE;
import com.itaca.ztool.api.util.UTIL_SET_PANID_RESPONSE;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_RSP;
import com.itaca.ztool.api.zdo.ZDO_BIND_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_BIND_RSP;
import com.itaca.ztool.api.zdo.ZDO_END_DEVICE_ANNCE_IND;
import com.itaca.ztool.api.zdo.ZDO_END_DEVICE_ANNCE_SRSP;
import com.itaca.ztool.api.zdo.ZDO_END_DEVICE_BIND_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_END_DEVICE_BIND_RSP;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_RSP;
import com.itaca.ztool.api.zdo.ZDO_MATCH_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_MATCH_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LEAVE_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LEAVE_RSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LQI_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LQI_RSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_PERMIT_JOIN_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_PERMIT_JOIN_RSP;
import com.itaca.ztool.api.zdo.ZDO_NODE_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_NODE_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_NWK_ADDR_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_NWK_ADDR_RSP;
import com.itaca.ztool.api.zdo.ZDO_SIMPLE_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_SIMPLE_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_STARTUP_FROM_APP_SRSP;
import com.itaca.ztool.api.zdo.ZDO_STATE_CHANGE_IND;
import com.itaca.ztool.api.zdo.ZDO_UNBIND_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_UNBIND_RSP;
import com.itaca.ztool.api.zdo.ZDO_USER_DESC_CONF;
import com.itaca.ztool.api.zdo.ZDO_USER_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_USER_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_USER_DESC_SET_SRSP;
import com.itaca.ztool.util.DoubleByte;
import com.itaca.ztool.util.IIntArrayInputStream;

/**
 * Reads a packet from the input stream, verifies checksum and creates an XBeeResponse object
 *
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZToolPacketStream implements IIntArrayInputStream {

    //private final static Logger log = Logger.getLogger(ZToolPacketStream.class);
    private InputStream in;
    private int length;
    private Checksum checksum = new Checksum();
    private boolean done = false;
    private int bytesRead;
    private int escapeBytes;
    private ZToolPacket response;
    private int apiIdMSB;
    private int apiIdLSB;
    private DoubleByte apiId;
    private boolean generic = false;
    private int[] frameData;

    public ZToolPacketStream(InputStream in) {
        this.in = in;
    }

    public ZToolPacket parsePacket() throws IOException {

        Exception exception = null;

        try {
            
            //int byteLength = this.read("Length");
            this.length = this.read("Length");           
            //log.debug("data length is " + ByteUtils.formatByte(length.getLength()));
            frameData = new int[length];
            this.apiIdMSB = this.read("API ID MSB");
            this.apiIdLSB = this.read("API ID LSB");
            this.apiId = new DoubleByte(this.apiIdMSB, this.apiIdLSB);
            if (generic) {
                //log.info("Parsing data as generic");
                int i = 0;
                // Read all data bytes without parsing
                while (i < frameData.length) {
                    frameData[i] = this.read("Data " + i + "-th");
                    i++;
                }
                response = new ZToolPacket(this.apiId, this.frameData);
            } else {
                frameData = this.readRemainingBytes();
                switch (apiId.get16BitValue()) {
                    case ZToolCMD.SYS_ADC_READ_SRSP:
                        response = new SYS_ADC_READ_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_RESET_RESPONSE:
                        response = new SYS_RESET_RESPONSE(frameData);
                        break;
                    case ZToolCMD.SYS_VERSION_RESPONSE:
                        response = new SYS_VERSION_RESPONSE(frameData);
                        break;
                    case ZToolCMD.SYS_PING_RESPONSE:
                        response = new SYS_PING_RESPONSE(frameData);
                        break;
                    case ZToolCMD.SYS_OSAL_NV_READ_SRSP:
                        response = new SYS_OSAL_NV_READ_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_OSAL_NV_WRITE_SRSP:
                        response = new SYS_OSAL_NV_WRITE_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_OSAL_START_TIMER_SRSP:
                        response = new SYS_OSAL_START_TIMER_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_OSAL_STOP_TIMER_SRSP:
                        response = new SYS_OSAL_STOP_TIMER_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_OSAL_TIMER_EXPIRED_IND:
                        response = new SYS_OSAL_TIMER_EXPIRED_IND(frameData);
                        break;
                    case ZToolCMD.SYS_RANDOM_SRSP:
                        response = new SYS_RANDOM_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_RPC_ERROR:
                        response = new SYS_RPC_ERROR(frameData);
                        break;
                    case ZToolCMD.SYS_GPIO_SRSP:
                        response = new SYS_GPIO_SRSP(frameData);
                        break;
                    case ZToolCMD.SYS_TEST_LOOPBACK_SRSP:
                        response = new SYS_TEST_LOOPBACK_SRSP(frameData);
                        break;
                    case ZToolCMD.AF_DATA_CONFIRM:
                        response = new AF_DATA_CONFIRM(frameData);
                        break;
                    case ZToolCMD.AF_DATA_SRSP:
                        response = new AF_DATA_SRSP(frameData);
                        break;
                    case ZToolCMD.AF_INCOMING_MSG:
                        response = new AF_INCOMING_MSG(frameData);
                        break;
                    case ZToolCMD.AF_REGISTER_SRSP:
                        response = new AF_REGISTER_SRSP(frameData);
                        break;
                    case ZToolCMD.ZB_ALLOW_BIND_CONFIRM:
                        response = new ZB_ALLOW_BIND_CONFIRM();
                        break;
                    case ZToolCMD.ZB_ALLOW_BIND_RSP:
                        response = new ZB_ALLOW_BIND_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_APP_REGISTER_RSP:
                        response = new ZB_APP_REGISTER_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_BIND_CONFIRM:
                        response = new ZB_BIND_CONFIRM(frameData);
                        break;
                    case ZToolCMD.ZB_BIND_DEVICE_RSP:
                        response = new ZB_BIND_DEVICE_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_FIND_DEVICE_CONFIRM:
                        response = new ZB_FIND_DEVICE_CONFIRM(frameData);
                        break;
                    case ZToolCMD.ZB_FIND_DEVICE_REQUEST_RSP:
                        response = new ZB_FIND_DEVICE_REQUEST_RSP();
                        break;
                    case ZToolCMD.ZB_GET_DEVICE_INFO_RSP:
                        response = new ZB_GET_DEVICE_INFO_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_PERMIT_JOINING_REQUEST_RSP:
                        response = new ZB_PERMIT_JOINING_REQUEST_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_READ_CONFIGURATION_RSP:
                        response = new ZB_READ_CONFIGURATION_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_RECEIVE_DATA_INDICATION:
                        response = new ZB_RECEIVE_DATA_INDICATION(frameData);
                        break;
                    case ZToolCMD.ZB_SEND_DATA_CONFIRM:
                        response = new ZB_SEND_DATA_CONFIRM(frameData);
                        break;
                    case ZToolCMD.ZB_SEND_DATA_REQUEST_RSP:
                        response = new ZB_SEND_DATA_REQUEST_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_START_CONFIRM:
                        response = new ZB_START_CONFIRM(frameData);
                        break;
                    case ZToolCMD.ZB_START_REQUEST_RSP:
                        response = new ZB_START_REQUEST_RSP(frameData);
                        break;
                    case ZToolCMD.ZB_WRITE_CONFIGURATION_RSP:
                        response = new ZB_WRITE_CONFIGURATION_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_ACTIVE_EP_REQ_SRSP:
                        response = new ZDO_ACTIVE_EP_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_ACTIVE_EP_RSP:
                        response = new ZDO_ACTIVE_EP_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_BIND_REQ_SRSP:
                        response = new ZDO_BIND_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_BIND_RSP:
                        response = new ZDO_BIND_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_END_DEVICE_ANNCE_IND:
                        response = new ZDO_END_DEVICE_ANNCE_IND(frameData);
                        break;
                    case ZToolCMD.ZDO_END_DEVICE_ANNCE_SRSP:
                        response = new ZDO_END_DEVICE_ANNCE_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_END_DEVICE_BIND_REQ_SRSP:
                        response = new ZDO_END_DEVICE_BIND_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_END_DEVICE_BIND_RSP:
                        response = new ZDO_END_DEVICE_BIND_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_IEEE_ADDR_REQ_SRSP:
                        response = new ZDO_IEEE_ADDR_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_IEEE_ADDR_RSP:
                        response = new ZDO_IEEE_ADDR_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MATCH_DESC_REQ_SRSP:
                        response = new ZDO_MATCH_DESC_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MATCH_DESC_RSP:
                        response = new ZDO_MATCH_DESC_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_LEAVE_REQ_SRSP:
                        response = new ZDO_MGMT_LEAVE_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_LEAVE_RSP:
                        response = new ZDO_MGMT_LEAVE_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_LQI_REQ_SRSP:
                        response = new ZDO_MGMT_LQI_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_LQI_RSP:
                        response = new ZDO_MGMT_LQI_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_PERMIT_JOIN_REQ_SRSP:
                        response = new ZDO_MGMT_PERMIT_JOIN_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_MGMT_PERMIT_JOIN_RSP:
                        response = new ZDO_MGMT_PERMIT_JOIN_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_NODE_DESC_REQ_SRSP:
                        response = new ZDO_NODE_DESC_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_NODE_DESC_RSP:
                        response = new ZDO_NODE_DESC_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_NWK_ADDR_REQ_SRSP:
                        response = new ZDO_NWK_ADDR_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_NWK_ADDR_RSP:
                        response = new ZDO_NWK_ADDR_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_SIMPLE_DESC_REQ_SRSP:
                        response = new ZDO_SIMPLE_DESC_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_SIMPLE_DESC_RSP:
                        response = new ZDO_SIMPLE_DESC_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_STATE_CHANGE_IND:
                        response = new ZDO_STATE_CHANGE_IND(frameData);
                        break;
                    case ZToolCMD.ZDO_UNBIND_REQ_SRSP:
                        response = new ZDO_UNBIND_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_UNBIND_RSP:
                        response = new ZDO_UNBIND_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_USER_DESC_REQ_SRSP:
                        response = new ZDO_USER_DESC_REQ_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_USER_DESC_RSP:
                        response = new ZDO_USER_DESC_RSP(frameData);
                        break;
                    case ZToolCMD.ZDO_USER_DESC_CONF:
                        response = new ZDO_USER_DESC_CONF(frameData);
                        break;
                    case ZToolCMD.ZDO_USER_DESC_SET_SRSP:
                        response = new ZDO_USER_DESC_SET_SRSP(frameData);
                        break;
                    case ZToolCMD.ZDO_STARTUP_FROM_APP_SRSP:
                        response = new ZDO_STARTUP_FROM_APP_SRSP(frameData);
                        break;
                    case ZToolCMD.UTIL_SET_PANID_RESPONSE:
                        response = new UTIL_SET_PANID_RESPONSE(frameData);
                        break;
                    case ZToolCMD.UTIL_SET_CHANNELS_RESPONSE:
                        response = new UTIL_SET_CHANNELS_RESPONSE(frameData);
                        break;
                    default:
                        response = new ZToolPacket(this.apiId, this.frameData);
                }
            }
            //response.setFCS(this.read("Checksum"));
            int fcs = this.read("Checksum");
            //setDone(true);
            if (fcs != response.getFCS()) {
                //log.debug("Checksum of packet failed: received =" + fcs + " expected = " + response.getFCS());
                throw new ZToolParseException("Packet checksum failed");
            }
            if (!this.isDone()) {
                // TODO this is not the answer!
                throw new ZToolParseException("Packet stream is not finished yet we seem to think it is");
            }
            //Response fields are replaced with the read values, to show errors in case we decide to remove exceptions
            //response.setLEN(length);
            //response.setCMD(apiId);
            //response.setFCS(fcs);
        } catch (Exception e) {
            //log.error("Failed due to exception", e);
            exception = e;
        }

        if (response == null) {
            response = new ErrorPacket();
        }

        if (exception != null) {
            response.setError(true);
            response.setErrorMsg(exception.getMessage());
//			response.setException(e);
        }

        return response;
    }

    public int read(String context) throws IOException {
        int b = this.read();
        //log.debug("Read " + context + " byte, val is " + ByteUtils.formatByte(b));
        return b;
    }

    /**
     * TODO implement as class that extends inputstream?
     * 
     * This method reads bytes from the underlying input stream and performs the following tasks:
     * keeps track of how many bytes we've read, un-escapes bytes if necessary and verifies the checksum.
     */
    public int read() throws IOException {

        if (done) {
            throw new ZToolParseException("Packet has read all of its bytes");
        }

        int b = in.read();

        if (b == -1) {
            throw new ZToolParseException("Read -1 from input stream while reading packet!");
        }

        bytesRead++;

        // when verifying checksum you must add the checksum that we are verifying
        // when computing checksum, do not include start byte; when verifying, include checksum
        checksum.addByte(b);
        //log.debug("Read byte " + ByteUtils.formatByte(b) + " at position " + bytesRead + ", data length is " + this.length.getLength() + ", #escapeBytes is " + escapeBytes + ", remaining bytes is " + this.getRemainingBytes());

        if (this.getFrameDataBytesRead() >= (length + 1)) {
            // this is checksum and final byte of packet
            done = true;

            //log.debug("Checksum byte is " + b);
        /*
        if (!checksum.verify()) {/////////////Maybe expected in ZTool is 0x00, not FF////////////////////
        throw new ZToolParseException("Checksum is incorrect.  Expected 0xff, but got " + checksum.getChecksum());
        }
         */
        }

        return b;
    }

    

    // TODO verify
    private int[] readRemainingBytes() throws IOException {
        int[] value = new int[length - this.getFrameDataBytesRead()];

        for (int i = 0; i < value.length; i++) {
            value[i] = this.read("Remaining bytes " + (value.length-i));
        //log.debug("byte " + i + " is " + value[i]);
        }

        return value;
    }

    public ZToolAddress64 parseAddress64() throws IOException {
        ZToolAddress64 addr = new ZToolAddress64();
        byte[] ieee = addr.getAddress();
        for (int i = 0; i < 8; i++) {
            ieee[i] = (byte) this.read("64-bit Address byte " + i);
        }

        return addr;
    }

    public ZToolAddress16 parseAddress16() throws IOException {
        ZToolAddress16 addr16 = new ZToolAddress16();

        addr16.setMsb(this.read("Address 16 MSB"));
        addr16.setLsb(this.read("Address 16 LSB"));

        return addr16;
    }

    /**
     * Returns number of bytes remaining, relative to the stated packet length (not including checksum).
     * @return
     */
    public int getFrameDataBytesRead() {
        // subtract out the 1 length bytes and API ID 2 bytes
        return this.getBytesRead() - 3;
    }

    /**
     * Number of bytes remaining to be read, including the checksum
     * @return
     */
    public int getRemainingBytes() {
        // add one for checksum byte (not included) in packet length
        return length - this.getFrameDataBytesRead() + 1;
    }

    // get unescaped packet length
    // get escaped packet length
    /**
     * Does not include any escape bytes
     * @return
     */
    public int getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(int bytesRead) {
        this.bytesRead = bytesRead;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getChecksum() {
        return checksum.getChecksum();
    }
}
