function Alerts(props){
  return(
    <>
      <div className="alert-container" ref={props.inputRef}>
        <div className="alert-title">waiting reservation</div>
        <div className="alert-contents">
          <p>{props.contents}</p>
        </div>
      </div>
    </>
  )
}
export default Alerts;