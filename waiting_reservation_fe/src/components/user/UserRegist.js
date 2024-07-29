import { useEffect, useState, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/StoreRegist.css'
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
const StoreRegist = (props) =>{
  const {role} = useContext(WaitingReservationContext);
  const [addressOnOff,setAddressOnOff] = useState(false);
  const [storeName,setStoreName] = useState("");
  const [address, setAddress] = useState("");
  const navigator = useNavigate();


  const registStore=()=>{
    const tk = localStorage.getItem("jwt")
    axios.post((`http://localhost:8080/api/store/owner/register`),{
      storeName: storeName,
      businessRegistrationUrl: "string",
      address: address
    },{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      navigator("/");
    }).catch(err=>{
      console.log(err);
    })
  }

  const handleComplete = (data) => {
    console.log(data)
    setAddress(data.address);
  }
  const selectAddress = (e) => {
    open({ onComplete: handleComplete });
  }
  
  return (
    <>
      <div className="StoreRegist-container">
        <div className='StoreRegist-main-container'>
          <div className='StoreRegist-info-container'>
            <div className='StoreRegist-info-sub-container'>
              <label htmlFor='store-name'>식당명</label>
              <input type='text' name='store-name' onChange={handleSetStoreName} value={storeName} className='StoreRegist-store-name-input' placeholder='식당 이름을 적어주세요.'/>
            </div>
          </div>

          <div className='StoreRegist-info-container'>
            <div className='StoreRegist-info-sub-address-container'>
              <label htmlFor='address'>주소</label>
              <input type='text' name='address' className='StoreRegist-address-input' value={address} placeholder='주소를 적어주세요'/>
              <input type='button' value={"주소찾기"} onClick={selectAddress} className='address-btn'/>
            </div>
          </div>

          <div className='StoreRegist-info-container'>
            <div className='StoreRegist-info-sub-file-btn-container'>
              <div className='StoreRegist-info-file-container'>
                <div className='StoreRegist-info-file-sub-container'>
                  <label htmlFor='store-name'>사업자 등록증</label>
                  <input type='file' name='business-registration' className='StoreRegist-business-registration-input'/>
                </div>
              </div>
              <div className='StoreRegist-btn-container'>
                <input type='button' onClick={registStore} value={"등록"} className='regist-btn'/>
              </div>
            </div>
            

          </div>
          
        </div>
        
      </div>
    </>
  );
}

export default StoreRegist;