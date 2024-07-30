import { useEffect, useState, useContext, useRef } from 'react';
import '../../styles/Waiting.css'
import CommonItem from './CommonItem';
import axios from "axios";
import WaitingReservationContext from "../provider/WaitingReservationContext";
import { useNavigate } from "react-router-dom";
import Alerts from "../Alerts";
import '../../styles/Alerts.css'
const WaitingInfo = (props) =>{
  const [reaminCount, setReaminCount] = useState("");
  const [menuList,setMenuList] = useState([]);
  const [storeId, setStoreId] = useState("");
  const {orderMenuList,handleResetOrderMenuList,username,name} = useContext(WaitingReservationContext);
  const navigator = useNavigate();

  useEffect(()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setReaminCount(res.data.body.remainingCount);
      setStoreId(res.data.body.storeId)
      menuInfo(res.data.body.storeId);
    }).catch(err=>{
      console.log(err);
    })
  },[])



  //메뉴 정보 조회
  const menuInfo = (storeId) =>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/open-api/menu/${storeId}`))
    .then(res=>{
      console.log(res.data.body);
      setMenuList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }

  //예약 정보 조회
  const check=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setReaminCount(res.data.body.remainingCount);
    }).catch(err=>{
      console.log(err);
    })
  }


  //알림
  const [alertContent,setAlertContent] = useState("");
  //성공
  const alerSuccesstRef = useRef("");

  const handleSuccessAlert = (res) =>{
    console.log(res)
    let price = 0;
    let content = ""
    res.data.body.map(it=>{
        price += it.cost;
        console.log(it)
        content += `${it.title} : ${it.count}개\n`
      })
      console.log(content)
    setAlertContent(`${content}\n가격 : ${price}`);
    alerSuccesstRef.current.classList.add("visible");
    setTimeout(()=> {
      alerSuccesstRef.current.classList.remove("visible");
    }, 2000);
  }
  //실패
  const alerFailtRef = useRef("");

  const handleFailAlert = (data) =>{
    console.log(data)
    if(data.response !== undefined && data.response.status === 403){
      setAlertContent("로그인을 해주세요.");
    }else if(data.response.data.result.resultDescription !== null){
      setAlertContent(data.response.data.result.resultDescription);
    }
    alerFailtRef.current.classList.add("visible");
    setTimeout(()=> {
      alerFailtRef.current.classList.remove("visible");
    }, 2000);
  }

  const handleNotAddMenuAlert = (data) =>{
    setAlertContent(data);
    alerFailtRef.current.classList.add("visible");
    setTimeout(()=> {
      alerFailtRef.current.classList.remove("visible");
    }, 2000);
  }

  //주문
  const order = (id) =>{
    console.log(orderMenuList)
    if(orderMenuList.length !== 0){
      const tk = localStorage.getItem("jwt")
      axios.post((`http://localhost:8080/api/order/${id}`),
      orderMenuList,{
        headers:{
          Authorization: tk
        }
      }).then(res=>{
        handleSuccessAlert(res)
        handleResetOrderMenuList();
      }).catch(err=>{
        handleFailAlert(err);
        console.log(err);
      })
    }else{
      handleNotAddMenuAlert("메뉴를 추가해 주세요.")
    }
    
  }
  console.log(username)

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
                  '-' : `남은 순서 : ${reaminCount}`
              }
            </div>
            <div className='Waiting-count-info-button-container'>
              <input type='button' className='count-btn' onClick={check} value={'조회'}/>
              <input type='button' className='menu-btn' value={"주문"} onClick={()=>order(storeId)}/>
              <input type='button' className='menu-btn' value={"내역"} onClick={()=>navigator('/waiting/order',{state:{storeId:storeId,username:username,name:name}})}/>
            </div>
          </div>
        </div>
        <div className='Waiting-menu-order-container'>
          <div className='Waiting-menu-list-container'>
            {
              menuList.map((item)=>(
                <CommonItem key={item.id} item={item}/>
              ))
            }
          </div>
          
        </div>
      </div>
    </>
  );
}

export default WaitingInfo;