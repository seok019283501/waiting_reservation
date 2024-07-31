import { useState, useContext, useRef } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/StoreRegist.css'
import { useDaumPostcodePopup } from "react-daum-postcode";
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
const StoreRegist = (props) =>{
  const {role} = useContext(WaitingReservationContext);
  const [addressOnOff,setAddressOnOff] = useState(false);
  const [storeName,setStoreName] = useState("");
  const [address, setAddress] = useState("");
  const navigator = useNavigate();

  const open = useDaumPostcodePopup("https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js");
  

  const handleSetStoreName = (e) =>{
    setStoreName(e.target.value);
  }


  

  const handleComplete = (data) => {
    setAddress(data.address);
  }
  const selectAddress = (e) => {
    open({ onComplete: handleComplete });
  }

  // 파일 선택창을 직접 제어하기 위해 ref 객체 변수를 정의
  const refFiles = useRef();

  // 업로드할 파일 데이터를 저장할 상태변수와 이벤트 핸들러
  const [file, setFile] = useState("");
  const handlerChangeFiles = e => {
    const file = e.target.files;

    if (file.length > 1) {
        alert('이미지는 1개만 업로드 가능합니다.');
        refFiles.current.value = '';
        setFile("");
        return;
    }

    setFile(file[0]);
  };
  


  //식당 등록
  const registStore=()=>{
    const tk = localStorage.getItem("jwt")
    // 서버로 전달할 입력창 내용을 객체로 정의 (단축 속성명)
    let datas = { storeName,address };

    // FormData 변수에 서버로 전달할 입력창의 내용을 data 이름으로 설정
    const formData = new FormData();

    formData.append('data', new Blob([JSON.stringify(datas)], { type: 'application/json' }));
    // 첨부 파일을 files 이름으로 추가
    formData.append('file', file)
    axios.post((`http://localhost:8080/api/store/owner/register`),
      formData
    ,{
      headers:{
        Authorization: tk,
        'Content-Type' : 'multipart/form-data'
      }
    }).then(res=>{
      navigator("/");
    }).catch(err=>{
      console.log(err);
    })
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
                  <input ref={refFiles} type='file' name='business-registration' onChange={handlerChangeFiles} className='StoreRegist-business-registration-input'/>
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