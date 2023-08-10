import { styled } from 'styled-components';
import Button from '../components/common/button/BaseButton';
import ButtonLarge from '../components/common/button/ButtonLarge';
import ButtonSmall from '../components/common/button/ButtonSmall';
import ColorCodeInput from '../components/common/ColorCodeInput';
import TabButton from '../components/common/TabButton';
import InformationTag from '../components/common/InformationTag';
import TextInput from '../components/common/TextInput';
import ProgressIndicator from '../components/common/ProgressIndicator';
import DropdownIndicator from '../components/common/DropdownIndicator';
import DropdownPanel from '../components/common/DropdownPanel';
import TextArea from '../components/common/TextArea';
import SideBar from '../components/common/SideBar';
import FilterBar from '../components/common/FilterBar';
import Comment from '../components/common/CommentElements';
import { AppContext } from '../main';
import { useContext } from 'react';

export default function Components() {
  const appContext = useContext(AppContext);
  const { changeTheme } = appContext.control;
  return (
    <StyledComponents>
      <Wrapper>
        <ButtonLarge type="submit" iconName="plus">
          BUTTON
        </ButtonLarge>
        <Button type="submit" outline iconName="plus">
          BUTTON
        </Button>
        <Button type="submit" ghost flexible iconName="plus">
          This is Flexible BUTTON
        </Button>
        <ButtonSmall type="submit" ghost selected iconName="plus">
          BUTTON
        </ButtonSmall>
        <ButtonSmall type="submit" ghost iconName="plus" onClick={changeTheme}>
          BUTTON
        </ButtonSmall>
        <ColorCodeInput label="배경색" />
        <TabButton />
        <InformationTag size="medium" iconName="alertCircle">
          Label
        </InformationTag>
        <TextInput
          id="test1"
          name="test1"
          size="tall"
          labelName="label"
          placeholder="placeholder"
          helpText="5자리 이상 입력해주세요."
          validationFunc={(value) => {
            return value.length >= 5;
          }}
        />
        <TextInput
          id="test2"
          name="test2"
          size="short"
          labelName="label"
          placeholder="placeholder"
          helpText="Caption"
        />
        <ProgressIndicator openCount={0} closeCount={0} />
        <DropdownIndicator text="Button" />
        <DropdownPanel label="label" />
        <TextArea
          labelName="코멘트 작성"
          placeholder="코멘트를 작성해주세요."
        />
        <Comment
          userInfo={{ userName: '@fuse' }}
          timeStamp="5분 전"
          comment="오늘 점심 뭐 먹죠?"
        />
        <SideBar />
        <FilterBar />
      </Wrapper>
    </StyledComponents>
  );
}

const StyledComponents = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
`;
