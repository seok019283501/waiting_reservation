import { useEffect, useState, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/StoreRegist.css'
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
const StoreRegist = (props) =>{
  const {role} = useContext(WaitingReservationContext);

  const navigator = useNavigate();

  //예약 유무
  const [status,setStatus] = useState(false);
  //예약
  const reservation = () =>{
    const tk = localStorage.getItem("jwt")
    axios.post((`http://localhost:8080/api/reservation/${props.item.id}`),{},{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setStatus(true);
    }).catch(err=>{
      console.log(err);
    })
  }

  
  useEffect(()=>{
    if(role !== "ROLE_OWNER"){
      reservationStatus();

    }
  },[])

  //예약 유무 확인
  const reservationStatus=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info/${props.item.id}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setStatus(true);
    }).catch(err=>{
      console.log(err);
    })
  }
  
  return (
    <>
      <div className="StoreRegist-container">
        <div className='StoreRegist-main-container'>
          <div className='StoreRegist-store-name-container'>
            <label htmlFor='store-name'>식당명</label>
            <input type='text' name='store-name' className='StoreRegist-store-name-input'/>
          </div>
          <div className='StoreRegist-store-name-container'>
            <label htmlFor='store-name'>주소</label>
            <input type='text' name='store-name' className='StoreRegist-store-name-input'/>
          </div>
          <div className='StoreRegist-store-name-container'>
            <label htmlFor='store-name'>식당명</label>
            <input type='text' name='store-name' className='StoreRegist-store-name-input'/>
          </div>
        </div>
        
      </div>
    </>
  );
}

export default StoreRegist;