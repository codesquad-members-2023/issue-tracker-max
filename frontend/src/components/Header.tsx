import { styled } from "styled-components";

export function Header() {
  return (
    <Div>
      <img
        style={{ width: "200px", height: "40px" }}
        src={`/src/assets/logoDark.svg`}
        alt="logo 아이콘"
      />
      <img
        style={{ width: "32px" }}
        src="https://avatars.githubusercontent.com/u/41321198?v=4"
      />
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: inline-flex;
  padding: 27px 80px;
  justify-content: space-between;
  align-items: center;
`;
