import { useEffect, useState,useContext, useRef } from 'react';
import '../../styles/Waiting.css'
import CommonItem from './CommonItem';
import { useLocation } from 'react-router-dom';
import WaitingReservationContext from "../provider/WaitingReservationContext";
import Alerts from "../Alerts";
import '../../styles/Alerts.css'
const WaitingList = (props) =>{
  const location = useLocation();

  const {waitingUserList,userList,reaminCount} = useContext(WaitingReservationContext);
  useEffect(()=>{
    waitingUserList(location.state.id);
  },[])


  //알림
  const [alertContent,setAlertContent] = useState("");
  //성공
  const alerSuccesstRef = useRef("");

  const handleSuccessAlert = (res,name) =>{
    setAlertContent(`${name}님 입장`);
    alerSuccesstRef.current.classList.add("visible");
    setTimeout(()=> {
      alerSuccesstRef.current.classList.remove("visible");
    }, 2000);
  }
  //실패
  const alerFailtRef = useRef("");
  
  const handleFailAlert = (data) =>{
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
      <div className="Waiting-container">
      <Alerts inputRef={alerSuccesstRef} contents={alertContent}/>
      <Alerts inputRef={alerFailtRef} contents={alertContent}/>
        <div className='Waiting-count-container'>
          <div className='Waiting-count-sub-container'>
            <div className='Waiting-count-info-container'>
              {
                reaminCount === "" || reaminCount ===undefined ? 
                  '-' : `남은 수 : ${reaminCount}`
              }
            </div>
            <div className='Waiting-count-info-button-container'>
            <input type='button' className='count-btn' onClick={()=>waitingUserList(location.state.id)} value={'조회'}/>
            </div>
          </div>
        </div>
        <div className='Waiting-menu-order-container'>
          <div className='Waiting-menu-list-container'>
              {
                userList.map((item)=>(
                  <CommonItem key={item.id} item={item} handleSuccessAlert={handleSuccessAlert} handleFailAlert={handleFailAlert}/>
                ))
              }
          </div>
        </div>
        
      </div>
    </>
  );
}

export default WaitingList;