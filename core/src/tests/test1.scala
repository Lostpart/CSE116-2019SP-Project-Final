package tests
import objects._
import physics._
import org.scalatest._


class test1 extends FunSuite{

  def loc_same(loc1: Array[Double], loc2: Array[Double]):Boolean={
    loc1(0)==loc2(0) && loc1(1)==loc2(1)
  }
  test("locationfixed test"){
    var item1:Item = new PlayerSub("test1")
    var distantmoved: Array[Double] = Array(0,0)
    var Board:Array[Int] = Array(500,500)


    // auto generate Test # 1
    item1.location = Array(439.0,440.0)
    item1.speed = 76.0
    distantmoved = Array(426.0,430.0)
    Board = Array(779,359)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(426.0,359.0)))

    // auto generate Test # 2
    item1.location = Array(138.0,648.0)
    item1.speed = 22.0
    distantmoved = Array(178.0,635.0)
    Board = Array(394,641)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(178.0,635.0)))

    // auto generate Test # 3
    item1.location = Array(462.0,367.0)
    item1.speed = 67.0
    distantmoved = Array(426.0,337.0)
    Board = Array(327,724)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(327.0,337.0)))

    // auto generate Test # 4
    item1.location = Array(613.0,119.0)
    item1.speed = 37.0
    distantmoved = Array(588.0,139.0)
    Board = Array(538,497)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(538.0,139.0)))

    // auto generate Test # 5
    item1.location = Array(550.0,846.0)
    item1.speed = 6.0
    distantmoved = Array(586.0,810.0)
    Board = Array(1060,926)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(586.0,810.0)))

    // auto generate Test # 6
    item1.location = Array(327.0,119.0)
    item1.speed = 81.0
    distantmoved = Array(329.0,78.0)
    Board = Array(521,423)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(329.0,78.0)))

    // auto generate Test # 7
    item1.location = Array(627.0,592.0)
    item1.speed = 44.0
    distantmoved = Array(587.0,547.0)
    Board = Array(871,1081)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(587.0,547.0)))

    // auto generate Test # 8
    item1.location = Array(447.0,305.0)
    item1.speed = 76.0
    distantmoved = Array(473.0,295.0)
    Board = Array(1150,700)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(473.0,295.0)))

    // auto generate Test # 9
    item1.location = Array(678.0,471.0)
    item1.speed = 31.0
    distantmoved = Array(665.0,421.0)
    Board = Array(679,252)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(665.0,252.0)))

    // auto generate Test # 10
    item1.location = Array(525.0,232.0)
    item1.speed = 89.0
    distantmoved = Array(519.0,243.0)
    Board = Array(389,1074)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(389.0,243.0)))

    // auto generate Test # 11
    item1.location = Array(355.0,39.0)
    item1.speed = 38.0
    distantmoved = Array(383.0,79.0)
    Board = Array(1162,324)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(383.0,79.0)))

    // auto generate Test # 12
    item1.location = Array(380.0,479.0)
    item1.speed = 82.0
    distantmoved = Array(333.0,458.0)
    Board = Array(591,986)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(333.0,458.0)))

    // auto generate Test # 13
    item1.location = Array(496.0,935.0)
    item1.speed = 16.0
    distantmoved = Array(489.0,981.0)
    Board = Array(1172,1001)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(489.0,981.0)))

    // auto generate Test # 14
    item1.location = Array(815.0,233.0)
    item1.speed = 0.0
    distantmoved = Array(838.0,280.0)
    Board = Array(1146,1019)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(838.0,280.0)))

    // auto generate Test # 15
    item1.location = Array(68.0,535.0)
    item1.speed = 36.0
    distantmoved = Array(19.0,531.0)
    Board = Array(655,1048)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(19.0,531.0)))

    // auto generate Test # 16
    item1.location = Array(85.0,592.0)
    item1.speed = 65.0
    distantmoved = Array(72.0,561.0)
    Board = Array(746,930)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(72.0,561.0)))

    // auto generate Test # 17
    item1.location = Array(294.0,956.0)
    item1.speed = 10.0
    distantmoved = Array(247.0,962.0)
    Board = Array(1244,416)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(247.0,416.0)))

    // auto generate Test # 18
    item1.location = Array(257.0,514.0)
    item1.speed = 5.0
    distantmoved = Array(306.0,503.0)
    Board = Array(696,823)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(306.0,503.0)))

    // auto generate Test # 19
    item1.location = Array(617.0,0.0)
    item1.speed = 77.0
    distantmoved = Array(627.0,46.0)
    Board = Array(392,1064)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(392.0,46.0)))

    // auto generate Test # 20
    item1.location = Array(502.0,6.0)
    item1.speed = 9.0
    distantmoved = Array(528.0,-36.0)
    Board = Array(368,425)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(368.0,0.0)))

    // auto generate Test # 21
    item1.location = Array(678.0,608.0)
    item1.speed = 21.0
    distantmoved = Array(717.0,599.0)
    Board = Array(265,1018)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(265.0,599.0)))

    // auto generate Test # 22
    item1.location = Array(873.0,485.0)
    item1.speed = 12.0
    distantmoved = Array(913.0,494.0)
    Board = Array(475,1015)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(475.0,494.0)))

    // auto generate Test # 23
    item1.location = Array(529.0,956.0)
    item1.speed = 49.0
    distantmoved = Array(552.0,964.0)
    Board = Array(833,748)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(552.0,748.0)))

    // auto generate Test # 24
    item1.location = Array(69.0,537.0)
    item1.speed = 15.0
    distantmoved = Array(30.0,583.0)
    Board = Array(314,505)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(30.0,505.0)))

    // auto generate Test # 25
    item1.location = Array(332.0,819.0)
    item1.speed = 79.0
    distantmoved = Array(300.0,796.0)
    Board = Array(923,281)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(300.0,281.0)))

    // auto generate Test # 26
    item1.location = Array(172.0,115.0)
    item1.speed = 40.0
    distantmoved = Array(219.0,99.0)
    Board = Array(1217,990)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(219.0,99.0)))

    // auto generate Test # 27
    item1.location = Array(698.0,771.0)
    item1.speed = 26.0
    distantmoved = Array(664.0,737.0)
    Board = Array(312,1132)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(312.0,737.0)))

    // auto generate Test # 28
    item1.location = Array(473.0,89.0)
    item1.speed = 47.0
    distantmoved = Array(511.0,121.0)
    Board = Array(354,432)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(354.0,121.0)))

    // auto generate Test # 29
    item1.location = Array(27.0,998.0)
    item1.speed = 85.0
    distantmoved = Array(73.0,1037.0)
    Board = Array(845,701)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(73.0,701.0)))

    // auto generate Test # 30
    item1.location = Array(878.0,726.0)
    item1.speed = 14.0
    distantmoved = Array(891.0,680.0)
    Board = Array(1094,1244)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(891.0,680.0)))

    // auto generate Test # 31
    item1.location = Array(443.0,225.0)
    item1.speed = 6.0
    distantmoved = Array(420.0,214.0)
    Board = Array(820,1147)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(420.0,214.0)))

    // auto generate Test # 32
    item1.location = Array(541.0,857.0)
    item1.speed = 11.0
    distantmoved = Array(526.0,881.0)
    Board = Array(425,416)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(425.0,416.0)))

    // auto generate Test # 33
    item1.location = Array(708.0,401.0)
    item1.speed = 48.0
    distantmoved = Array(750.0,364.0)
    Board = Array(548,1242)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(548.0,364.0)))

    // auto generate Test # 34
    item1.location = Array(172.0,199.0)
    item1.speed = 73.0
    distantmoved = Array(208.0,240.0)
    Board = Array(385,1088)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(208.0,240.0)))

    // auto generate Test # 35
    item1.location = Array(350.0,618.0)
    item1.speed = 63.0
    distantmoved = Array(336.0,599.0)
    Board = Array(1038,1176)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(336.0,599.0)))

    // auto generate Test # 36
    item1.location = Array(45.0,604.0)
    item1.speed = 22.0
    distantmoved = Array(69.0,578.0)
    Board = Array(1006,580)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(69.0,578.0)))

    // auto generate Test # 37
    item1.location = Array(370.0,910.0)
    item1.speed = 41.0
    distantmoved = Array(404.0,949.0)
    Board = Array(265,959)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(265.0,949.0)))

    // auto generate Test # 38
    item1.location = Array(710.0,772.0)
    item1.speed = 22.0
    distantmoved = Array(662.0,727.0)
    Board = Array(1184,628)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(662.0,628.0)))

    // auto generate Test # 39
    item1.location = Array(811.0,930.0)
    item1.speed = 50.0
    distantmoved = Array(809.0,903.0)
    Board = Array(992,353)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(809.0,353.0)))

    // auto generate Test # 40
    item1.location = Array(489.0,2.0)
    item1.speed = 49.0
    distantmoved = Array(503.0,18.0)
    Board = Array(313,757)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(313.0,18.0)))

    // auto generate Test # 41
    item1.location = Array(281.0,820.0)
    item1.speed = 66.0
    distantmoved = Array(326.0,773.0)
    Board = Array(565,668)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(326.0,668.0)))

    // auto generate Test # 42
    item1.location = Array(752.0,167.0)
    item1.speed = 88.0
    distantmoved = Array(716.0,171.0)
    Board = Array(925,824)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(716.0,171.0)))

    // auto generate Test # 43
    item1.location = Array(108.0,617.0)
    item1.speed = 72.0
    distantmoved = Array(88.0,653.0)
    Board = Array(846,928)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(88.0,653.0)))

    // auto generate Test # 44
    item1.location = Array(460.0,365.0)
    item1.speed = 89.0
    distantmoved = Array(460.0,381.0)
    Board = Array(724,847)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(460.0,381.0)))

    // auto generate Test # 45
    item1.location = Array(509.0,286.0)
    item1.speed = 41.0
    distantmoved = Array(483.0,258.0)
    Board = Array(572,1053)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(483.0,258.0)))

    // auto generate Test # 46
    item1.location = Array(180.0,914.0)
    item1.speed = 39.0
    distantmoved = Array(187.0,886.0)
    Board = Array(1222,909)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(187.0,886.0)))

    // auto generate Test # 47
    item1.location = Array(170.0,722.0)
    item1.speed = 72.0
    distantmoved = Array(166.0,703.0)
    Board = Array(773,1187)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(166.0,703.0)))

    // auto generate Test # 48
    item1.location = Array(29.0,366.0)
    item1.speed = 24.0
    distantmoved = Array(32.0,354.0)
    Board = Array(521,675)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(32.0,354.0)))

    // auto generate Test # 49
    item1.location = Array(405.0,298.0)
    item1.speed = 34.0
    distantmoved = Array(407.0,262.0)
    Board = Array(433,838)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(407.0,262.0)))

    // auto generate Test # 50
    item1.location = Array(557.0,373.0)
    item1.speed = 4.0
    distantmoved = Array(584.0,399.0)
    Board = Array(273,984)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(273.0,399.0)))

    // auto generate Test # 51
    item1.location = Array(831.0,556.0)
    item1.speed = 82.0
    distantmoved = Array(872.0,589.0)
    Board = Array(503,400)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(503.0,400.0)))

    // auto generate Test # 52
    item1.location = Array(651.0,848.0)
    item1.speed = 18.0
    distantmoved = Array(677.0,807.0)
    Board = Array(817,367)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(677.0,367.0)))

    // auto generate Test # 53
    item1.location = Array(109.0,170.0)
    item1.speed = 88.0
    distantmoved = Array(63.0,139.0)
    Board = Array(327,886)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(63.0,139.0)))

    // auto generate Test # 54
    item1.location = Array(865.0,516.0)
    item1.speed = 69.0
    distantmoved = Array(870.0,480.0)
    Board = Array(315,571)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(315.0,480.0)))

    // auto generate Test # 55
    item1.location = Array(771.0,212.0)
    item1.speed = 71.0
    distantmoved = Array(805.0,220.0)
    Board = Array(1147,362)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(805.0,220.0)))

    // auto generate Test # 56
    item1.location = Array(457.0,781.0)
    item1.speed = 73.0
    distantmoved = Array(419.0,773.0)
    Board = Array(465,648)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(419.0,648.0)))

    // auto generate Test # 57
    item1.location = Array(836.0,778.0)
    item1.speed = 8.0
    distantmoved = Array(825.0,728.0)
    Board = Array(316,1068)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(316.0,728.0)))

    // auto generate Test # 58
    item1.location = Array(354.0,725.0)
    item1.speed = 45.0
    distantmoved = Array(384.0,741.0)
    Board = Array(425,1016)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(384.0,741.0)))

    // auto generate Test # 59
    item1.location = Array(550.0,327.0)
    item1.speed = 24.0
    distantmoved = Array(562.0,362.0)
    Board = Array(322,371)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(322.0,362.0)))

    // auto generate Test # 60
    item1.location = Array(289.0,519.0)
    item1.speed = 61.0
    distantmoved = Array(287.0,509.0)
    Board = Array(678,787)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(287.0,509.0)))

    // auto generate Test # 61
    item1.location = Array(694.0,212.0)
    item1.speed = 59.0
    distantmoved = Array(655.0,220.0)
    Board = Array(1022,1059)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(655.0,220.0)))

    // auto generate Test # 62
    item1.location = Array(638.0,169.0)
    item1.speed = 23.0
    distantmoved = Array(673.0,199.0)
    Board = Array(975,1070)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(673.0,199.0)))

    // auto generate Test # 63
    item1.location = Array(173.0,195.0)
    item1.speed = 57.0
    distantmoved = Array(139.0,244.0)
    Board = Array(500,1118)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(139.0,244.0)))

    // auto generate Test # 64
    item1.location = Array(409.0,658.0)
    item1.speed = 74.0
    distantmoved = Array(392.0,650.0)
    Board = Array(323,319)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(323.0,319.0)))

    // auto generate Test # 65
    item1.location = Array(387.0,24.0)
    item1.speed = 7.0
    distantmoved = Array(369.0,63.0)
    Board = Array(297,790)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(297.0,63.0)))

    // auto generate Test # 66
    item1.location = Array(941.0,494.0)
    item1.speed = 81.0
    distantmoved = Array(970.0,532.0)
    Board = Array(423,802)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(423.0,532.0)))

    // auto generate Test # 67
    item1.location = Array(469.0,21.0)
    item1.speed = 60.0
    distantmoved = Array(461.0,1.0)
    Board = Array(603,818)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(461.0,1.0)))

    // auto generate Test # 68
    item1.location = Array(370.0,155.0)
    item1.speed = 0.0
    distantmoved = Array(320.0,186.0)
    Board = Array(1060,373)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(320.0,186.0)))

    // auto generate Test # 69
    item1.location = Array(262.0,500.0)
    item1.speed = 66.0
    distantmoved = Array(236.0,509.0)
    Board = Array(372,288)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(236.0,288.0)))

    // auto generate Test # 70
    item1.location = Array(299.0,281.0)
    item1.speed = 79.0
    distantmoved = Array(261.0,246.0)
    Board = Array(398,1107)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(261.0,246.0)))

    // auto generate Test # 71
    item1.location = Array(528.0,309.0)
    item1.speed = 20.0
    distantmoved = Array(547.0,313.0)
    Board = Array(558,1104)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(547.0,313.0)))

    // auto generate Test # 72
    item1.location = Array(503.0,549.0)
    item1.speed = 55.0
    distantmoved = Array(528.0,533.0)
    Board = Array(902,677)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(528.0,533.0)))

    // auto generate Test # 73
    item1.location = Array(424.0,495.0)
    item1.speed = 78.0
    distantmoved = Array(386.0,458.0)
    Board = Array(348,1000)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(348.0,458.0)))

    // auto generate Test # 74
    item1.location = Array(528.0,827.0)
    item1.speed = 7.0
    distantmoved = Array(494.0,823.0)
    Board = Array(557,895)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(494.0,823.0)))

    // auto generate Test # 75
    item1.location = Array(120.0,950.0)
    item1.speed = 7.0
    distantmoved = Array(134.0,964.0)
    Board = Array(266,572)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(134.0,572.0)))

    // auto generate Test # 76
    item1.location = Array(63.0,739.0)
    item1.speed = 85.0
    distantmoved = Array(52.0,780.0)
    Board = Array(373,448)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(52.0,448.0)))

    // auto generate Test # 77
    item1.location = Array(695.0,97.0)
    item1.speed = 48.0
    distantmoved = Array(711.0,84.0)
    Board = Array(1228,312)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(711.0,84.0)))

    // auto generate Test # 78
    item1.location = Array(655.0,334.0)
    item1.speed = 59.0
    distantmoved = Array(631.0,348.0)
    Board = Array(897,744)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(631.0,348.0)))

    // auto generate Test # 79
    item1.location = Array(469.0,108.0)
    item1.speed = 67.0
    distantmoved = Array(419.0,115.0)
    Board = Array(1187,518)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(419.0,115.0)))

    // auto generate Test # 80
    item1.location = Array(263.0,733.0)
    item1.speed = 44.0
    distantmoved = Array(309.0,741.0)
    Board = Array(662,629)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(309.0,629.0)))

    // auto generate Test # 81
    item1.location = Array(101.0,940.0)
    item1.speed = 86.0
    distantmoved = Array(67.0,898.0)
    Board = Array(1152,1042)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(67.0,898.0)))

    // auto generate Test # 82
    item1.location = Array(885.0,85.0)
    item1.speed = 52.0
    distantmoved = Array(852.0,95.0)
    Board = Array(809,1218)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(809.0,95.0)))

    // auto generate Test # 83
    item1.location = Array(23.0,709.0)
    item1.speed = 35.0
    distantmoved = Array(-15.0,691.0)
    Board = Array(678,569)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(0.0,569.0)))

    // auto generate Test # 84
    item1.location = Array(312.0,758.0)
    item1.speed = 64.0
    distantmoved = Array(295.0,709.0)
    Board = Array(616,316)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(295.0,316.0)))

    // auto generate Test # 85
    item1.location = Array(210.0,301.0)
    item1.speed = 27.0
    distantmoved = Array(221.0,277.0)
    Board = Array(390,490)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(221.0,277.0)))

    // auto generate Test # 86
    item1.location = Array(122.0,115.0)
    item1.speed = 15.0
    distantmoved = Array(96.0,156.0)
    Board = Array(1127,786)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(96.0,156.0)))

    // auto generate Test # 87
    item1.location = Array(975.0,374.0)
    item1.speed = 38.0
    distantmoved = Array(980.0,334.0)
    Board = Array(612,1000)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(612.0,334.0)))

    // auto generate Test # 88
    item1.location = Array(302.0,2.0)
    item1.speed = 1.0
    distantmoved = Array(268.0,-8.0)
    Board = Array(1115,452)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(268.0,0.0)))

    // auto generate Test # 89
    item1.location = Array(188.0,973.0)
    item1.speed = 41.0
    distantmoved = Array(234.0,1019.0)
    Board = Array(1080,1101)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(234.0,1019.0)))

    // auto generate Test # 90
    item1.location = Array(220.0,147.0)
    item1.speed = 20.0
    distantmoved = Array(218.0,140.0)
    Board = Array(357,675)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(218.0,140.0)))

    // auto generate Test # 91
    item1.location = Array(955.0,635.0)
    item1.speed = 70.0
    distantmoved = Array(983.0,684.0)
    Board = Array(727,905)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(727.0,684.0)))

    // auto generate Test # 92
    item1.location = Array(749.0,164.0)
    item1.speed = 56.0
    distantmoved = Array(777.0,211.0)
    Board = Array(590,992)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(590.0,211.0)))

    // auto generate Test # 93
    item1.location = Array(259.0,876.0)
    item1.speed = 22.0
    distantmoved = Array(282.0,837.0)
    Board = Array(362,1191)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(282.0,837.0)))

    // auto generate Test # 94
    item1.location = Array(930.0,983.0)
    item1.speed = 34.0
    distantmoved = Array(949.0,1009.0)
    Board = Array(442,591)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(442.0,591.0)))

    // auto generate Test # 95
    item1.location = Array(461.0,214.0)
    item1.speed = 14.0
    distantmoved = Array(417.0,189.0)
    Board = Array(723,983)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(417.0,189.0)))

    // auto generate Test # 96
    item1.location = Array(222.0,252.0)
    item1.speed = 78.0
    distantmoved = Array(254.0,233.0)
    Board = Array(1198,1128)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(254.0,233.0)))

    // auto generate Test # 97
    item1.location = Array(368.0,523.0)
    item1.speed = 24.0
    distantmoved = Array(341.0,507.0)
    Board = Array(1005,772)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(341.0,507.0)))

    // auto generate Test # 98
    item1.location = Array(455.0,748.0)
    item1.speed = 81.0
    distantmoved = Array(455.0,723.0)
    Board = Array(1054,1114)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(455.0,723.0)))

    // auto generate Test # 99
    item1.location = Array(820.0,807.0)
    item1.speed = 79.0
    distantmoved = Array(831.0,813.0)
    Board = Array(1144,850)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(831.0,813.0)))

    // auto generate Test # 100
    item1.location = Array(620.0,271.0)
    item1.speed = 56.0
    distantmoved = Array(669.0,273.0)
    Board = Array(1121,873)
    Physics.locationFixed(item1,distantmoved,Board)
    assert(loc_same(item1.location,Array(669.0,273.0)))


  }
}
