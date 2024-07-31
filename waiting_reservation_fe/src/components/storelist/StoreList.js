
import { useEffect, useState ,useContext, useRef} from 'react';
import '../../styles/StoreList.css'
import axios from "axios";
import StoreListItem from './StoreListItem';
import WaitingReservationContext from "../provider/WaitingReservationContext";
import { useNavigate } from 'react-router-dom';
import Alerts from "../Alerts";
import '../../styles/Alerts.css'
const StoreList = (props) =>{
  const list = ["전체","서울","경기도","인천","충남","충북","대전",
    "강원도","제주도","전북","전남","광주","경북","경남","부산","울산"];

  const [address, setAddress] = useState("전체");
  const [storeName, setStoreName] = useState("");
  const [storeList, setStoreList] = useState([]);

  const {role} = useContext(WaitingReservationContext);


  const navigator = useNavigate();
  //주소
  const handleSetAddress= (e) =>{
    setAddress(e.target.value);
  }

  //식당 이름
  const handleSetStoreName = (e) =>{
    setStoreName(e.target.value);
  }

  //식당 검색
  const search = () => {
    if(role === "ROLE_OWNER"){
      console.log("ROLE_OWNER")
    }else{
      openSearch();
    }
    
    
  }

  useEffect(()=>{
    console.log(role)
    if(role === "ROLE_OWNER"){
      ownerStoreSearch();
    }else if(role === "ROLE_ADMIN"){
      navigator("/admin/store");
    }else{
      openSearch();

    }

    
    
  },[role])

  //식당 전체 검색
  const openSearch=()=>{
    axios.get((`http://localhost:8080/open-api/store/search?${storeName !== '' ? `store_name=${storeName}` : null}&${address !== '전체' ? `address=${address}` : null}`))
    .then(res=>{
      setStoreList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }

  // 사업자 식당 리스트
  const ownerStoreSearch=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/store/search?${storeName !== '' ? `store_name=${storeName}` : null}&${address !== '전체' ? `address=${address}` : null}`),{
      headers:{
        Authorization: tk
      }
    })
    .then(res=>{
      setStoreList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }


  //알림
  const [alertContent,setAlertContent] = useState("");
  //성공
  const alerSuccesstRef = useRef("");

  const handleSuccessAlert = (data) =>{
    console.log(data)
    setAlertContent("예약이 완료되었습니다.")
    alerSuccesstRef.current.classList.add("visible");
    setTimeout(()=> {
      alerSuccesstRef.current.classList.remove("visible");
    }, 2000);
  }
  //실패
  const alerFailtRef = useRef("");

  const handleFailAlert = (data) =>{
    console.log(data)
    if(data.response.status === 403){
      setAlertContent("로그인을 해주세요.");
    }else if(data.response.data.result.resultDescription !== null){
      setAlertContent(data.response.data.result.resultDescription);
    }
    alerFailtRef.current.classList.add("visible");
    setTimeout(()=> {
      alerFailtRef.current.classList.remove("visible");
    }, 2000);
  }
  
  return (
    <>
      <div className="StoreList-container">
        <Alerts inputRef={alerSuccesstRef} contents={alertContent}/>
        <Alerts inputRef={alerFailtRef} contents={alertContent}/>
        <div className="StoreList-serch-container">
          <div className="StoreList-serch-input-container">
            <input type="text" className="StoreList-serch" onChange={handleSetStoreName} placeholder="식당 이름을 적어주세요."/>
            <input type="button" className="StoreList-serch-btn" onClick={search} value={"검색"}/>
          </div>
          <div className='StoreList-serch-select-address-container'>
            <select onChange={handleSetAddress} value={address}>
              {
                list.map((item)=>(
                  <option value={item}>
                    {item}
                  </option>
                ))
              }
            </select>
          </div>
        </div>
          <div className="StoreList-list-container">
            <div className='StoreList-list-sub-container'>
              {
                storeList.map((item)=>(
                  <StoreListItem key={item.id} item={item} handleSuccessAlert={handleSuccessAlert} handleFailAlert={handleFailAlert}/>
                ))
              }
            </div>
          </div>
        
      </div>
    </>
  );
}

export default StoreList;