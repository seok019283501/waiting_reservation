
import { useEffect, useState } from 'react';
import '../../styles/StoreList.css'
import axios from "axios";
import StoreListItem from './StoreListItem';
const StoreList = (props) =>{
  const list = ["전체","서울특별시","경기도","인천광역시","충청남도","충청북도","대전광역시",
    "강원도","제주도","전라북도","전라남도","광주광역시","경상북도","경상남도","부산광역시","울산광역시"];

  const [address, setAddress] = useState("전체");
  const [storeName, setStoreName] = useState("");
  const [storeList, setStoreList] = useState([]);

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
    axios.get((`http://localhost:8080/open-api/store/search?${storeName !== '' ? `store_name=${storeName}` : null}&${address !== '전체' ? `address=${address}` : null}`))
      .then(res=>{
        setStoreList(res.data.body);
      }).catch(err=>{
        console.log(err);
      })
    
  }

  useEffect(()=>{
    axios.get((`http://localhost:8080/open-api/store/search`))
    .then(res=>{
      setStoreList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  },[])
  
  return (
    <>
      <div className="StoreList-container">
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
                  <StoreListItem key={item.id} item={item}/>
                ))
              }
            </div>
          </div>
        
      </div>
    </>
  );
}

export default StoreList;