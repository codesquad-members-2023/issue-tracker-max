import { styled } from "styled-components";
import { Icon } from "./Icon";

export function Header() {
  return (
    <Div>
      <Icon name="logoLightMedium" />
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
